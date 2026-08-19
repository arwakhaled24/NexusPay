package com.example.nexuspay.feature.home.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.GlassMutedText

@Composable
fun HomeEmptyTransactionsContent() {
    Text(
        text = "No transactions yet",
        modifier = Modifier.padding(vertical = Dimens.SpacingLg),
        style = MaterialTheme.typography.bodyMedium,
        color = GlassMutedText,
    )
}