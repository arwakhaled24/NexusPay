package com.example.nexuspay.feature.home.presentation.composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.NexusPayPrimary
@Composable
 fun RecentTransactionsHeader(onSeeAllClick: () -> Unit) {
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
            modifier = Modifier.clickable(onClick = onSeeAllClick),
            style = MaterialTheme.typography.labelLarge,
            color = NexusPayPrimary,
        )
    }
}