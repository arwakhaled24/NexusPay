package com.example.nexuspay.feature.cards.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.feature.cards.presentation.componant.ActionButton
import com.example.nexuspay.feature.cards.presentation.componant.CardDetailsSection
import com.example.nexuspay.feature.cards.presentation.componant.SpendingControlsSection
import com.example.nexuspay.feature.cards.presentation.componant.VirtualCardsSection
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CardsScreen(
    onNavigateToAddCard: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CardsViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.singleEvent.collectLatest { event ->
            when (event) {
                CardsContract.Events.NavigateToAddCard -> onNavigateToAddCard()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Dimens.ScreenHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VirtualCardsSection(
            cards = state.cards,
        )
        ActionButton(
            text = "Add New Card",
            onClick = { viewModel.processIntent(CardsContract.Actions.AddCardClicked) },
        )
        CardDetailsSection()
        SpendingControlsSection()

    }
}
