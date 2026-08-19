package com.example.nexuspay.feature.cards.presentation.componant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexuspay.R

@Composable
fun CardDetailsSection(
    modifier: Modifier = Modifier,
    cardType: String="Physical + Virtual",
    status: String ="Active & Secure",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "CARD DETAILS",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            CardDetailItem(
                iconID = R.drawable.safty_icon,
                label = "Status",
                value = status,
            )
            CardDetailItem(
                iconID = R.drawable.card_icon,
                label = "Card Type",
                value = cardType,
            )
        }
    }
}