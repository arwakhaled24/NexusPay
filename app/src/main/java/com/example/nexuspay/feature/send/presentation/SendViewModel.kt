package com.example.nexuspay.feature.send.presentation

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.asFlow
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.nexuspay.core.data.models.Transaction.Resource
import com.example.nexuspay.core.viewmodel.NexusPayViewModel
import com.example.nexuspay.feature.send.domain.usecase.GetSendContactsUC
import com.example.nexuspay.feature.transactions.data.model.SendTransactionPayload
import com.example.nexuspay.feature.transactions.data.worker.TransactionSyncWorker
import com.example.nexuspay.feature.transactions.domain.model.PendingRequest
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestStatus
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import com.example.nexuspay.feature.transactions.domain.usecase.SavePendingRequestUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SendViewModel @Inject constructor(
    private val getSendContacts: GetSendContactsUC,
    private val savePendingRequest: SavePendingRequestUC,
    private val userRepository: IUserRepository,
    private val workManager: WorkManager,
    private val json: Json,
) : NexusPayViewModel<SendContract.Actions, SendContract.Events, SendContract.State>(
    SendContract.State(),
) {
    init {
        loadContacts()
    }

    override fun onActionTrigger(action: SendContract.Actions) {
        when (action) {
            SendContract.Actions.Retry -> loadContacts()
            is SendContract.Actions.SelectUser -> setState(oldViewState.copy(selectedUser = action.user))
            is SendContract.Actions.UpdateAmount -> setState(
                oldViewState.copy(amount = oldViewState.amount.appendDigit(action.digit)),
            )
            SendContract.Actions.RemoveAmountDigit -> setState(
                oldViewState.copy(
                    amount = oldViewState.amount.removeLastDigit(),
                ),
            )
            is SendContract.Actions.UpdateNote -> setState(oldViewState.copy(note = action.note))
            SendContract.Actions.ConfirmSend -> saveSendRequest()
        }
    }

    override fun clearState() {
        setState(SendContract.State())
    }

    private fun loadContacts() {
        getSendContacts(viewModelScope, Unit) { result ->
            when (result) {
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> setState(
                    oldViewState.copy(
                        isLoading = false,
                        contacts = result.model,
                        exception = null,
                    ),
                )
                is Resource.Failure -> setState(
                    oldViewState.copy(
                        isLoading = false,
                        exception = result.exception,
                    ),
                )
            }
        }
    }

    private fun saveSendRequest() {
        val selectedUser = oldViewState.selectedUser ?: return
        val amount = oldViewState.amount
        if (amount == "0") return

        viewModelScope.launch {
            val payload = SendTransactionPayload(
                senderIdentifier = userRepository.getIdentifier(),
                receiverIdentifier = selectedUser.identifier,
                amount = amount.toLong(),
                currency = "GBP",
                title = "Sent to ${selectedUser.name}",
            )
            val pendingRequest = PendingRequest(
                localId = UUID.randomUUID().toString(),
                type = PendingRequestType.SEND_TRANSACTION,
                payload = json.encodeToString(payload),
                createdAt = System.currentTimeMillis(),
                status = PendingRequestStatus.PENDING,
            )

            savePendingRequest(viewModelScope, pendingRequest) { result ->
                if (result is Resource.Success) {
                    workManager.enqueue(TransactionSyncWorker.buildRequest(pendingRequest))
                    setState(oldViewState.copy(localId = pendingRequest.localId, isSending = true))
                    observeWorkerCompletion(pendingRequest.localId)
                }
            }
        }
    }

    private fun observeWorkerCompletion(localId: String) {
        viewModelScope.launch {
            workManager.getWorkInfosByTagLiveData(localId)
                .asFlow()
                .collectLatest { workInfos ->
                    when {
                        workInfos.any { it.state == WorkInfo.State.SUCCEEDED } -> {
                            setState(oldViewState.copy(localId = null, isSending = false))
                            sendEvent(SendContract.Events.ShowToast("Transfer sent successfully."))
                            sendEvent(SendContract.Events.NavigateBack)
                        }
                        workInfos.any { it.state == WorkInfo.State.FAILED } -> {
                            setState(oldViewState.copy(localId = null, isSending = false))
                            sendEvent(SendContract.Events.ShowToast("Transaction failed. Please try again."))
                        }
                    }
                }
        }
    }

    private fun String.appendDigit(digit: String): String = when {
        digit !in "0".."9" -> this
        this == "0" -> digit
        length >= 10 -> this
        else -> this + digit
    }

    private fun String.removeLastDigit(): String = when {
        length <= 1 -> "0"
        else -> dropLast(1)
    }
}