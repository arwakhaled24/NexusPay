package com.example.nexuspay.feature.home.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nexuspay.R
import com.example.nexuspay.design.components.OnLoading
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.GlassMutedText
import com.example.nexuspay.design.theme.GlassOutline
import com.example.nexuspay.design.theme.GlassSurface
import com.example.nexuspay.design.theme.NexusPayPrimary
import com.example.nexuspay.design.theme.NexusPaySuccess
import com.example.nexuspay.design.theme.TransactionIconSurface
import com.example.nexuspay.feature.home.domain.model.Transaction
import com.example.nexuspay.feature.home.domain.model.TransactionType
import com.example.nexuspay.feature.home.domain.model.User
import com.example.nexuspay.feature.home.presentation.MoneyFormatter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeSuccessContent(
    user: User,
    transactions: List<Transaction>,
    onSendMoneyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        HomeTopBar(user = user)
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
            item { RecentTransactionsHeader() }
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

@Composable
fun HomeLoadingContent(modifier: Modifier = Modifier) {
    OnLoading(modifier = modifier)
}

@Composable
fun HomeErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.HomeHorizontalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = GlassMutedText,
        )
        Spacer(Modifier.height(Dimens.SpacingLg))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
private fun HomeTopBar(user: User) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HomeTopBarHeight),
        color = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.HomeHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "${user.name} profile image",
                modifier = Modifier
                    .size(Dimens.IconSizeXl - Dimens.SpacingSm)
                    .clip(CircleShape),
            )
            Spacer(Modifier.width(Dimens.SpacingMd))
            Text(
                text = "NexusPay",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineLarge,
                color = NexusPayPrimary,
            )
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun CenteredHomeContent(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.widthIn(max = 350.dp).fillMaxWidth()) {
            content()
        }
    }
}

@Composable
private fun BalanceGlassCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.BalanceCardHeight),
        shape = RoundedCornerShape(Dimens.SpacingMd),
        border = BorderStroke(Dimens.SpacingXxs / 2, GlassOutline),
        colors = CardDefaults.cardColors(containerColor = GlassSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.bodyMedium,
                color = GlassMutedText,
            )
            Spacer(Modifier.height(Dimens.SpacingXs))
            Text(
                text = MoneyFormatter.format(user.balance),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun SendMoneyButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(Dimens.QuickActionWidth)
            .height(Dimens.QuickActionHeight),
        shape = CircleShape,
        border = BorderStroke(Dimens.SpacingXxs / 2, NexusPayPrimary.copy(alpha = 0.45f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = GlassSurface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        contentPadding = PaddingValues(horizontal = Dimens.SpacingLg),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send money",
            tint = NexusPayPrimary,
        )
        Spacer(Modifier.width(Dimens.SpacingMd))
        Text(text = "Send", style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun RecentTransactionsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.IconSizeLg),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Recent Transactions",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "See All",
            style = MaterialTheme.typography.labelLarge,
            color = NexusPayPrimary,
        )
    }
}

@Composable
private fun HomeEmptyTransactionsContent() {
    Text(
        text = "No transactions yet",
        modifier = Modifier.padding(vertical = Dimens.SpacingLg),
        style = MaterialTheme.typography.bodyMedium,
        color = GlassMutedText,
    )
}

@Composable
private fun TransactionCard(transaction: Transaction) {
    val isReceived = transaction.type == TransactionType.RECEIVED
    val amountColor = if (isReceived) NexusPaySuccess else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.TransactionCardMinHeight),
        shape = RoundedCornerShape(Dimens.SpacingMd),
        border = BorderStroke(Dimens.SpacingXxs / 2, GlassOutline),
        colors = CardDefaults.cardColors(containerColor = GlassSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.CardElevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.SpacingLg),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(Dimens.TransactionCardIconSize),
                shape = CircleShape,
                color = TransactionIconSurface,
            ) {
                Icon(
                    painter = painterResource(R.drawable.redoinf_icon),
                    contentDescription = if (isReceived) "Received transaction" else "Sent transaction",
                    modifier = Modifier.padding(Dimens.SpacingMd),
                    tint = amountColor,
                )
            }
            Spacer(Modifier.width(Dimens.SpacingLg))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = displayRecipientName(transaction.description),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = formatTimestamp(transaction.date, transaction.time),
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    color = GlassMutedText,
                )
            }
            Spacer(Modifier.width(Dimens.SpacingSm))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (isReceived) "+" else "-"}${MoneyFormatter.format(transaction.amount)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = amountColor,
                )
                Text(
                    text = transaction.state.name.lowercase(Locale.US)
                        .replaceFirstChar { it.titlecase(Locale.US) },
                    style = MaterialTheme.typography.labelSmall,
                    color = GlassMutedText,
                )
            }
        }
    }
}

private fun formatTimestamp(date: String, time: String): String {
    val formattedDate = runCatching {
        LocalDate.parse(date).format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US))
    }.getOrDefault(date)
    val formattedTime = runCatching {
        LocalTime.parse(time).format(DateTimeFormatter.ofPattern("h:mm a", Locale.US))
    }.getOrDefault(time.take(5))
    return "$formattedDate • $formattedTime"
}

private fun displayRecipientName(description: String): String =
    description
        .removePrefix("Sent to ")
        .removePrefix("Received from ")
