package com.example.nexuspay.feature.transactions.presentation

import androidx.lifecycle.viewModelScope
import com.example.nexuspay.core.data.models.Transaction.Resource
import com.example.nexuspay.core.viewmodel.NexusPayViewModel
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import com.example.nexuspay.feature.transactions.domain.repository.IPendingRequestRepository
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.usecase.ObserveTransactionsUC
import com.example.nexuspay.feature.transactions.domain.usecase.SyncTransactionsUC
import com.example.nexuspay.feature.transactions.presentation.receipt.ReceiptExporter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val observeTransactions: ObserveTransactionsUC,
    private val syncTransactions: SyncTransactionsUC,
    private val userRepository: IUserRepository,
    private val pendingRequestRepository: IPendingRequestRepository,
    private val receiptExporter: ReceiptExporter,
) : NexusPayViewModel<
    TransactionsContract.Actions,
    TransactionsContract.Events,
    TransactionsContract.State,
    >(TransactionsContract.State()) {

    init {
        observeTransactions()
        syncTransactions()
        observePendingRequests()
    }

    override fun onActionTrigger(action: TransactionsContract.Actions) {
        when (action) {
            TransactionsContract.Actions.Retry -> syncTransactions()
            is TransactionsContract.Actions.SelectTransaction -> setState(
                oldViewState.copy(selectedTransaction = action.transaction),
            )
            TransactionsContract.Actions.DismissReceiptActions -> setState(
                oldViewState.copy(selectedTransaction = null),
            )
            TransactionsContract.Actions.ShareSelectedReceipt -> shareSelectedReceipt()
            TransactionsContract.Actions.DownloadSelectedReceipt -> downloadSelectedReceipt()
        }
    }

    override fun clearState() {
        setState(TransactionsContract.State())
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            observeTransactions.execute().collectLatest { transactions ->
                setState(oldViewState.copy(transactions = transactions, isLoading = false))
            }
        }
    }

    private fun syncTransactions() {
        viewModelScope.launch {
            val identifier = userRepository.getIdentifier()
            syncTransactions(viewModelScope, identifier) { result ->
            when (result) {
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> setState(
                    oldViewState.copy(
                        isLoading = false,
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
    }

    private fun observePendingRequests() {
        viewModelScope.launch {
            pendingRequestRepository.observeByType(PendingRequestType.SEND_TRANSACTION.name)
                .collectLatest { pendingRequests ->
                setState(oldViewState.copy(pendingRequests = pendingRequests))
            }
        }
    }

    private fun shareSelectedReceipt() {
        val transaction = oldViewState.selectedTransaction ?: return
        viewModelScope.launch {
            runCatching { receiptExporter.createShareImage(transaction) }
                .onSuccess { uri ->
                    setState(oldViewState.copy(selectedTransaction = null))
                    sendEvent(TransactionsContract.Events.ShareReceipt(uri))
                }
                .onFailure {
                    sendEvent(TransactionsContract.Events.ShowToast("Unable to create receipt image."))
                }
        }
    }

    private fun downloadSelectedReceipt() {
        val transaction = oldViewState.selectedTransaction ?: return
        viewModelScope.launch {
            runCatching { receiptExporter.savePdf(transaction) }
                .onSuccess {
                    setState(oldViewState.copy(selectedTransaction = null))
                    sendEvent(TransactionsContract.Events.ShowToast("Receipt saved to Downloads."))
                }
                .onFailure {
                    sendEvent(TransactionsContract.Events.ShowToast("Unable to save receipt PDF."))
                }
        }
    }

}