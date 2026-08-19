package com.example.nexuspay.feature.cards.presentation.componant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexuspay.feature.cards.presentation.model.CardNetwork

@Composable
fun CardNetworkLogo(
    network: CardNetwork,
) {
    when (network) {
        CardNetwork.VISA -> VisaBadge()
        CardNetwork.MASTERCARD -> MastercardBadge()
        CardNetwork.AMERICAN_EXPRESS ->AmericanExpressBadge()
    }
}

@Composable
fun MastercardBadge() {
    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 32.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center,
    ) {
        Box(modifier = Modifier.size(width = 32.dp, height = 20.dp)) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color(0xCCEB001B))
                    .align(Alignment.CenterStart),
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color(0xCCF79E1B))
                    .align(Alignment.CenterEnd),
            )
        }
    }
}



@Composable
fun VisaBadge() {
    Text(
        text = "VISA",
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        letterSpacing = 2.sp,
    )
}
@Composable
fun AmericanExpressBadge() {
    Text(
        text = "AMEX",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
    )
}

fun detectCardNetwork(cardNumber: String): CardNetwork? {
    val number = cardNumber.filter(Char::isDigit)

    return when {
        Regex("^4[0-9]{12}(?:[0-9]{3})?$").matches(number) ->
            CardNetwork.VISA

        Regex("^(5[1-5][0-9]{14}|2(?:2[2-9][0-9]{2}|[3-6][0-9]{3}|7[0-1][0-9]{2}|720)[0-9]{12})$")
            .matches(number) ->
            CardNetwork.MASTERCARD

        Regex("^3[47][0-9]{13}$").matches(number) ->
            CardNetwork.AMERICAN_EXPRESS

        else -> CardNetwork.AMERICAN_EXPRESS
    }
}