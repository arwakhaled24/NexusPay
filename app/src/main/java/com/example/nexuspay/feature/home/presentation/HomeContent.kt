package com.example.nexuspay.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.feature.home.presentation.composable.HomeErrorContent
import com.example.nexuspay.feature.home.presentation.composable.HomeLoadingContent
import com.example.nexuspay.feature.home.presentation.composable.HomeSuccessContent

@Composable
fun HomeContent(
    state: HomeContract.HomeState,
    onRetry: () -> Unit,
    onSendMoneyClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading && state.user == null -> HomeLoadingContent(modifier)
        state.exception != null && state.user == null -> HomeErrorContent(
            message = state.exception.message,
            onRetry = onRetry,
            modifier = modifier,
        )
        state.user != null -> HomeSuccessContent(
            user = state.user,
            transactions = state.recentTransactions,
            pendingRequests = state.pendingRequests,
            onSendMoneyClick = onSendMoneyClick,
            onSeeAllClick = onSeeAllClick,
            modifier = modifier,
        )
    }
}
