package com.example.nexuspay.feature.cards.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.dp
import android.widget.Toast
import kotlinx.coroutines.flow.collectLatest
import com.example.nexuspay.feature.cards.presentation.componant.AddCardDetailsHeader
import com.example.nexuspay.feature.cards.presentation.componant.CardForm
import com.example.nexuspay.feature.cards.presentation.componant.VirtualCard
import com.example.nexuspay.feature.cards.presentation.componant.detectCardNetwork
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData

@Composable
fun AddCardScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddCardViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var cardNumber by rememberSaveable { mutableStateOf("") }
    var cardholderName by rememberSaveable { mutableStateOf("") }
    var expiryDate by rememberSaveable { mutableStateOf("") }
    var cvv by rememberSaveable { mutableStateOf("") }
    val network = detectCardNetwork(cardNumber)

    LaunchedEffect(viewModel) {
        viewModel.singleEvent.collectLatest { event ->
            when (event) {
                is AddCardContract.Events.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                AddCardContract.Events.NavigateBack -> onNavigateBack()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AddCardDetailsHeader(
            title = "Add New Card",
            subtitle = "Link a secure payment method to your digital vault.",
        )

        VirtualCard(
            card = VirtualCardData(
                cardholderName = cardholderName,
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                network = network,
            ),
        )

        CardForm(
            cardNumber = cardNumber,
            onCardNumberChange = { cardNumber = it },

            cardholderName = cardholderName,
            onCardholderNameChange = { cardholderName = it },

            expiryDate = expiryDate,
            onExpiryDateChange = { expiryDate = it },

            cvv = cvv,
            onCvvChange = { cvv = it },
        )

        Button(
            onClick = {
                viewModel.processIntent(
                    AddCardContract.Actions.SaveCard(
                        VirtualCardData(
                            cardNumber = cardNumber,
                            cardholderName = cardholderName.trim(),
                            expiryDate = expiryDate,
                            network = network,
                        )
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Card")
        }
    }
}
