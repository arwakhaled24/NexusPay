package com.example.nexuspay.feature.transactions.presentation.componant
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.design.theme.Dimens
@Composable
 fun SectionTitle() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Transactions",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(Dimens.SpacingXs))
        Text(
            text = "Review your recent activity across all accounts.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}