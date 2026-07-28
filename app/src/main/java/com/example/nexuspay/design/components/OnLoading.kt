package com.example.nexuspay.design.components

import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.nexuspay.R
import com.example.nexuspay.core.extensions.fullContentBlure
import kotlin.math.roundToInt

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    val text = stringResource(R.string.l_o_a_d_i_n_g)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            text.forEachIndexed { index, char ->
                LoadingCharacter(
                    char = char,
                    index = index,
                    textLength = text.length,
                )
            }
        }
    }
}

@Composable
private fun LoadingCharacter(
    char: Char,
    index: Int,
    textLength: Int,
) {
    if (char == ' ') {
        Text(
            text = char.toString(),
            color = Color.White,
            fontSize = 16.sp,
        )
        return
    }

    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition $index")
    val blurAmount by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(
                offsetMillis = 1000 / textLength * index,
            ),
        ),
        label = "loading blur $index",
    )

    Text(
        text = char.toString(),
        color = Color.White,
        fontSize = 16.sp,
        modifier = Modifier
            .graphicsLayer {
                renderEffect = BlurEffect(
                    radiusX = blurAmount,
                    radiusY = blurAmount,
                )
            }
            .then(
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
                    Modifier.fullContentBlure { blurAmount.roundToInt() }
                } else {
                    Modifier
                },
            ),
    )
}
