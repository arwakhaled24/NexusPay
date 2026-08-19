package com.example.nexuspay.feature.transactions.presentation.componant

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import com.example.nexuspay.feature.transactions.presentation.TransactionsContract

@Composable
fun TransactionHistoryContent(
    state: TransactionsContract.State,
    onRetry: () -> Unit,
    onTransactionLongPress: (TransactionListItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading && state.transactions.isEmpty() -> LoadingContent(modifier)
        state.exception != null && state.transactions.isEmpty() -> ErrorContent(
            message = state.exception.message,
            onRetry = onRetry,
            modifier = modifier,
        )
        else -> TransactionsList(
            transactions = state.transactions,
            pendingRequests = state.pendingRequests,
            onTransactionLongPress = onTransactionLongPress,
            modifier = modifier,
        )
    }
}









