package com.example.nexuspay.feature.transactions.presentation.componant
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.components.PendingRequestsSection
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
@Composable
 fun TransactionsList(
     transactions: List<TransactionListItem>,
    pendingRequests: List<PendingRequestEntity>,
    onTransactionLongPress: (TransactionListItem) -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.HomeHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLg),
        ) {

            item { SectionTitle() }
            if (pendingRequests.isNotEmpty()) {
                item { PendingRequestsSection(pendingRequests = pendingRequests) }
            }
            if (transactions.isEmpty()) {
                item { EmptyTransactionsContent() }
            } else {
                transactions
                    .groupBy { transaction -> transaction.date.ifBlank { "Pending" } }
                    .forEach { (date, groupedTransactions) ->
                        item(key = date) {
                            TransactionGroup(
                                title = date,
                                transactions = groupedTransactions,
                                onTransactionLongPress = onTransactionLongPress,
                            )
                        }
                    }
            }
            item { EndOfListSpacer() }
        }
    }
}