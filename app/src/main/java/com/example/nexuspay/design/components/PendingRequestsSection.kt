package com.example.nexuspay.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexuspay.design.theme.DarkOnSurfaceVariant
import com.example.nexuspay.design.theme.GlassSurface
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity

@Composable
fun PendingRequestsSection(
    pendingRequests: List<PendingRequestEntity>,
    modifier: Modifier = Modifier,
) {
    if (pendingRequests.isEmpty()) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(GlassSurface, RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            color = DarkOnSurfaceVariant,
            strokeWidth = 2.dp,
        )
        Text(
            text = "${pendingRequests.size} transfer${if (pendingRequests.size == 1) "" else "s"} pending",
            color = DarkOnSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}