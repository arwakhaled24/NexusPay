package com.example.nexuspay.feature.cards.presentation.componant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nexuspay.R

@Composable
fun CardSecurityInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF191C1F))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(
                    width = 25.33.dp,
                    height = 33.25.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Color(0xFFADC6FF).copy(alpha = 0.10f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.lock_icon),
                contentDescription = null,
                modifier = Modifier.size(
                    width = 9.33.dp,
                    height = 12.25.dp
                ),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "Your payment information is encrypted\n" +
                    "and never stored on our servers.\n" +
                    "Transactions are processed through an\n" +
                    "industry-standard secure gateway.",
            modifier = Modifier.weight(1f),
            color = Color(0xFFC1C6D7)
        )
    }
}