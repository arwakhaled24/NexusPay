package com.example.nexuspay.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    onNavigateToSendMoney: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.singleEvent.collectLatest { event ->
            when (event) {
                HomeContract.HomeEvents.NavigateToSendMoney -> onNavigateToSendMoney()
            }
        }
    }

    HomeContent(
        state = state,
        onRetry = { viewModel.processIntent(HomeContract.HomeActions.Retry) },
        onSendMoneyClick = {
            viewModel.processIntent(HomeContract.HomeActions.OnSendMoneyClick)
        },
        modifier = modifier,
    )
}
