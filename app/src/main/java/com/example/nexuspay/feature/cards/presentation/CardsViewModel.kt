package com.example.nexuspay.feature.cards.presentation

import androidx.lifecycle.viewModelScope
import com.example.nexuspay.core.viewmodel.NexusPayViewModel
import com.example.nexuspay.feature.cards.domain.usecase.ObserveCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CardsViewModel @Inject constructor(
    private val observeCardsUseCase: ObserveCardsUseCase, // renamed property
) : NexusPayViewModel<
CardsContract.Actions,
CardsContract.Events,
CardsContract.State,
>(CardsContract.State()) {

    init {
        observeCards()
    }

    private fun observeCards() {
        viewModelScope.launch {
            observeCardsUseCase()
                .collectLatest { cards ->
                    setState(oldViewState.copy(cards = cards))
                }
        }
    }

    override fun onActionTrigger(action: CardsContract.Actions) {
        when (action) {
            CardsContract.Actions.AddCardClicked ->
                sendEvent(CardsContract.Events.NavigateToAddCard)
        }
    }

    override fun clearState() {
        setState(CardsContract.State())
    }
}