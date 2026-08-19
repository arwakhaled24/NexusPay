package com.example.nexuspay.feature.cards.presentation.componant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
 fun CardForm(
    cardNumber: String,
    onCardNumberChange: (String) -> Unit,
    cardholderName: String,
    onCardholderNameChange: (String) -> Unit,
    expiryDate: String,
    onExpiryDateChange: (String) -> Unit,
    cvv: String,
    onCvvChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CardInputField(
            label = "Card Number",
            value = cardNumber,
            placeholder = "0000 0000 0000 0000",
            onValueChange = onCardNumberChange,
            isCardNumber = true
        )

        CardInputField(
            label = "Cardholder Name",
            value = cardholderName,
            placeholder = "ENTER FULL NAME",
            onValueChange = onCardholderNameChange
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardInputField(
                modifier = Modifier.weight(1f),
                label = "Expiry Date",
                value = expiryDate,
                placeholder = "MM/YY",
                onValueChange = onExpiryDateChange
            )

            CardInputField(
                modifier = Modifier.weight(1f),
                label = "CVV",
                value = cvv,
                placeholder = "•••",
                onValueChange = onCvvChange,
                isPassword = true
            )
        }

        CardSecurityInfo()
    }
}