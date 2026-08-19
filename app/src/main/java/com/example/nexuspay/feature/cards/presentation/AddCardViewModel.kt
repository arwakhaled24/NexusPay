package com.example.nexuspay.feature.cards.presentation

import androidx.lifecycle.viewModelScope
import com.example.nexuspay.core.data.models.Transaction.Resource
import com.example.nexuspay.core.viewmodel.NexusPayViewModel
import com.example.nexuspay.feature.cards.domain.usecase.AddCardUseCase
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val addCard: AddCardUseCase,
) : NexusPayViewModel<
    AddCardContract.Actions,
    AddCardContract.Events,
    AddCardContract.State,
    >(AddCardContract.State()) {

    override fun onActionTrigger(action: AddCardContract.Actions) {
        when (action) {
            is AddCardContract.Actions.SaveCard -> saveCard(action.card)
        }
    }

    override fun clearState() {
        setState(AddCardContract.State())
    }


    private fun saveCard(card: VirtualCardData) {
        if (!validateCard(card)) return

        addCard(viewModelScope, card) { result ->
            when (result) {
                is Resource.Progress -> {
                    setState(oldViewState.copy(isSaving = result.loading))
                }

                is Resource.Success -> {
                    setState(
                        oldViewState.copy(
                            isSaving = false,
                            exception = null
                        )
                    )

                    sendEvent(
                        AddCardContract.Events.ShowToast(
                            "Card added successfully."
                        )
                    )
                    sendEvent(AddCardContract.Events.NavigateBack)
                }

                is Resource.Failure -> {
                    setState(
                        oldViewState.copy(
                            isSaving = false,
                            exception = result.exception
                        )
                    )

                    sendEvent(
                        AddCardContract.Events.ShowToast(
                            "Unable to add card. Please try again."
                        )
                    )
                }
            }
        }
    }

    private fun validateCard(card: VirtualCardData): Boolean {
        if (card.cardholderName.length < 2) {
            sendEvent(
                AddCardContract.Events.ShowToast(
                    "Please enter a valid cardholder name."
                )
            )
            return false
        }

        if (card.cardNumber.length != 16) {
            sendEvent(
                AddCardContract.Events.ShowToast(
                    "Please enter a valid card number."
                )
            )
            return false
        }

        if (card.network == null) {
            sendEvent(
                AddCardContract.Events.ShowToast(
                    "Unsupported card network."
                )
            )
            return false
        }

        if (!isValidExpiryDate(card.expiryDate)) {
            sendEvent(
                AddCardContract.Events.ShowToast(
                    "Please enter a valid expiry date."
                )
            )
            return false
        }

        return true
    }
    private fun isValidExpiryDate(value: String): Boolean {
        val match = Regex("""^(0[1-9]|1[0-2])/(\d{2})$""")
            .matchEntire(value)
            ?: return false

        val month = match.groupValues[1].toInt()
        val year = 2000 + match.groupValues[2].toInt()

        val now = java.time.YearMonth.now()

        return year > now.year ||
                (year == now.year && month >= now.monthValue)
    }
}