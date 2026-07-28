package com.example.nexuspay.core.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

fun Modifier.fullContentBlure(blurRadius: () -> Int): Modifier {
    val radius = blurRadius().coerceAtLeast(0)
    return if (radius > 0) {
        blur(radius.dp)
    } else {
        this
    }
}
