package com.example.nexuspay.feature.cards.presentation.componant
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
@Composable
fun CarouselIndicators(
    count: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.wrapContentWidth(Alignment.CenterHorizontally),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(count) { index ->
            val isActive = index == currentPage
            val width: Dp by animateDpAsState(
                targetValue = if (isActive) 32.dp else 8.dp,
                animationSpec = tween(durationMillis = 300),
                label = "indicator_width_$index",
            )
            val color by animateColorAsState(
                targetValue = if (isActive) Color(0xFFADC6FF) else Color(0xFF323538),
                animationSpec = tween(durationMillis = 300),
                label = "indicator_color_$index",
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(width = width, height = 6.dp)
                    .clip(CircleShape)
                    .background(color),
            )
        }
    }
}