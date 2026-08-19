package com.example.nexuspay.feature.cards.presentation.componant
import android.net.Network
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexuspay.R
import com.example.nexuspay.feature.cards.presentation.model.CardNetwork
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData

@Composable
fun VirtualCard(
    card: VirtualCardData,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(331.5f / 209f)
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Image(
            painter = painterResource(R.drawable.card_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Text(
                    text = "Virtual Card",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                )

                CardNetworkLogo(network = card.network?: CardNetwork.VISA)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "•••• •••• •••• ${card.cardNumber.takeLast(4)}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CardMeta(
                        label = "CARD HOLDER",
                        value = card.cardholderName,
                    )

                    CardMeta(
                        label = "EXPIRES",
                        value = card.expiryDate,
                    )
                }
            }
        }
    }
}