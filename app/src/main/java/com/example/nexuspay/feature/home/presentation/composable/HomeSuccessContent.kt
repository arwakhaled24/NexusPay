package com.example.nexuspay.feature.home.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.feature.home.domain.model.User
import com.example.nexuspay.design.components.PendingRequestsSection
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem

@Composable
fun HomeSuccessContent(
    user: User,
    transactions: List<TransactionListItem>,
    pendingRequests: List<PendingRequestEntity>,
    onSendMoneyClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = Dimens.HomeHorizontalPadding,
                end = Dimens.HomeHorizontalPadding,
                top = Dimens.SpacingXl,
                bottom = Dimens.SpacingXxl,
            ),
            verticalArrangement = Arrangement.spacedBy(Dimens.TransactionListGap),
        ) {
            item {
                CenteredHomeContent {
                    BalanceGlassCard(user = user)
                }
            }
            item { Spacer(Modifier.height(Dimens.SpacingXxl)) }
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    SendMoneyButton(onClick = onSendMoneyClick)
                }
            }
            item { Spacer(Modifier.height(Dimens.SpacingXxl)) }
            if (pendingRequests.isNotEmpty()) {
                item { PendingRequestsSection(pendingRequests = pendingRequests) }
            }
            item { RecentTransactionsHeader(onSeeAllClick = onSeeAllClick) }
            if (transactions.isEmpty()) {
                item { HomeEmptyTransactionsContent() }
            } else {
                items(items = transactions, key = { it.id }) { transaction ->
                    TransactionCard(transaction = transaction)
                }
            }
        }
    }
}