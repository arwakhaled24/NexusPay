package com.example.nexuspay.feature.cards.presentation

import com.example.nexuspay.core.data.models.exception.NexusPayException
import com.example.nexuspay.core.viewmodel.ViewAction
import com.example.nexuspay.core.viewmodel.ViewEvent
import com.example.nexuspay.core.viewmodel.ViewState
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData

interface AddCardContract {
    sealed class Actions : ViewAction {
        data class SaveCard(val card: VirtualCardData) : Actions()
    }

    sealed class Events : ViewEvent {
        data class ShowToast(val message: String) : Events()
        data object NavigateBack : Events()
    }

    data class State(
        val isSaving: Boolean = false,
        val exception: NexusPayException? = null,
    ) : ViewState
}