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

@Composable
fun SpendingControlsSection(
    onlinePaymentsEnabled: Boolean = true,
    atmWithdrewEnabled: Boolean=false,
    spendingLimit: Float = 750.toFloat(),
    onOnlinePaymentsChanged: (Boolean) -> Unit = {},
    onAtmWithdrewChanged: (Boolean) -> Unit={},
    onSpendingLimitChanged: (Float) -> Unit={},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "SPENDING CONTROLS",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            SpendingControlToggle(
                title = "Online Payments",
                description = "Allow payments made online",
                checked = onlinePaymentsEnabled,
                onCheckedChange = onOnlinePaymentsChanged,
            )

            SpendingControlToggle(
                title = "Atm Withdrawals",
                description = "Enable cash access",
                checked = atmWithdrewEnabled,
                onCheckedChange = onAtmWithdrewChanged,
            )

            SpendingLimitSlider(
                value = spendingLimit,
                onValueChange = onSpendingLimitChanged,
            )
        }
    }
}