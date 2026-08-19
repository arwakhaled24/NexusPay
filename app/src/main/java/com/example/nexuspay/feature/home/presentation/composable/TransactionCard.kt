package com.example.nexuspay.feature.home.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import com.example.nexuspay.feature.transactions.presentation.componant.TransactionListRow

@Composable
fun TransactionCard(
    transaction: TransactionListItem,
    modifier: Modifier = Modifier,
) {
    TransactionListRow(transaction = transaction, modifier = modifier)
}
