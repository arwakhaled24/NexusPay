package com.example.nexuspay.feature.cards.presentation

import com.example.nexuspay.core.viewmodel.ViewAction
import com.example.nexuspay.core.viewmodel.ViewEvent
import com.example.nexuspay.core.viewmodel.ViewState
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData

interface CardsContract {
    sealed class Actions : ViewAction {
        data object AddCardClicked : Actions()
    }

    sealed class Events : ViewEvent {
        data object NavigateToAddCard : Events()
    }

    data class State(
        val cards: List<VirtualCardData> = emptyList(),
    ) : ViewState
}