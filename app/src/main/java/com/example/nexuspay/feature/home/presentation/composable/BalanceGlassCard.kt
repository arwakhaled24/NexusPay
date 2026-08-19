package com.example.nexuspay.feature.home.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.GlassMutedText
import com.example.nexuspay.design.theme.GlassOutline
import com.example.nexuspay.design.theme.GlassSurface
import com.example.nexuspay.feature.home.domain.model.User
import com.example.nexuspay.feature.home.presentation.MoneyFormatter


@Composable
 fun BalanceGlassCard(user: User) {
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