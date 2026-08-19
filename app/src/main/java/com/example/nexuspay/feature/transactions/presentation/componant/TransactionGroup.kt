package com.example.nexuspay.feature.transactions.presentation.componant

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nexuspay.R
import com.example.nexuspay.core.data.models.Transaction.Money
import com.example.nexuspay.design.theme.NexusPayTypography
import com.example.nexuspay.design.theme.NexusPaySuccess
import com.example.nexuspay.design.theme.TransactionIconSurface
import com.example.nexuspay.feature.home.presentation.MoneyFormatter
import com.example.nexuspay.feature.transactions.domain.model.TransactionDisplayStatus
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem

@Composable
fun TransactionGroup(
    title: String,
    transactions: List<TransactionListItem>,
    onTransactionLongPress: (TransactionListItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = NexusPayTypography.bodySmall,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
        )

        transactions.forEach { transaction ->
            TransactionListRow(transaction, onLongPress = { onTransactionLongPress(transaction) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionListRow(
    transaction: TransactionListItem,
    modifier: Modifier = Modifier,
    onLongPress: (() -> Unit)? = null,
) {
    val isReceived = transaction.type.equals("RECEIVED", ignoreCase = true)
    val amountPrefix = when {
        transaction.displayStatus == TransactionDisplayStatus.PENDING -> ""
        isReceived -> "+"
        else -> "-"
    }
    val amountColor = if (isReceived) NexusPaySuccess else MaterialTheme.colorScheme.onSurface
    val supportsReceiptActions = transaction.displayStatus == TransactionDisplayStatus.CONFIRMED &&
        transaction.state.equals("COMPLETED", ignoreCase = true)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = {},
                    onLongClick = if (supportsReceiptActions) onLongPress else null,
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = TransactionIconSurface,
            ) {
                Icon(
                    painter = painterResource(R.drawable.redoinf_icon),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = amountColor,
                )
            }
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.description.counterpartyName(),
                    style = MaterialTheme.typography.titleMedium,
                )
                if (transaction.displayStatus == TransactionDisplayStatus.PENDING) {
                    AssistChip(
                        onClick = {},
                        label = { Text("Pending") },
                        modifier = Modifier.padding(top = 4.dp),
                    )
                } else {
                    Text(
                        text = "${transaction.date} ${transaction.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "$amountPrefix${MoneyFormatter.format(Money(transaction.amount, transaction.currency))}",
                style = MaterialTheme.typography.titleMedium,
                color = amountColor,
            )
        }
    }
}

private fun String.counterpartyName(): String =
    removePrefix("Sent to ")
        .removePrefix("Received from ")
        .removePrefix("Sending to ")