package com.example.nexuspay.feature.send.presentation

import com.example.nexuspay.core.data.models.exception.NexusPayException
import com.example.nexuspay.core.viewmodel.ViewAction
import com.example.nexuspay.core.viewmodel.ViewEvent
import com.example.nexuspay.core.viewmodel.ViewState
import com.example.nexuspay.feature.send.domain.model.SendContact

interface SendContract {
    sealed class Actions : ViewAction {
        data object Retry : Actions()
        data class SelectUser(val user: SendContact) : Actions()
        data class UpdateAmount(val digit: String) : Actions()
        data object RemoveAmountDigit : Actions()
        data class UpdateNote(val note: String) : Actions()
        data object ConfirmSend : Actions()
    }

    sealed class Events : ViewEvent {
        data class ShowToast(val message: String) : Events()
        data object NavigateBack : Events()
    }

    data class State(
        val isLoading: Boolean = false,
        val contacts: List<SendContact> = emptyList(),
        val selectedUser: SendContact? = null,
        val amount: String = "0",
        val note: String = "",
        val localId: String? = null,
        val isSending: Boolean = false,
        val exception: NexusPayException? = null,
    ) : ViewState
}