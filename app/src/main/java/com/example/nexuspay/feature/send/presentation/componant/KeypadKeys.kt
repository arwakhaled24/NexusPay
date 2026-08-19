package com.example.nexuspay.feature.send.presentation.componant
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

 val KeypadKeys = listOf(
    listOf("1", "2", "3"),
    listOf("4", "5", "6"),
    listOf("7", "8", "9"),
    listOf(".", "0", ""),
)

@Composable
 fun NumericKeypad(
    onDigit: (String) -> Unit,
    onBackspace: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        KeypadKeys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                row.forEach { key ->
                    KeypadButton(
                        label = key,
                        modifier = Modifier
                            .weight(1f)
                            .height(72.dp),
                        onClick = {
                            if (key == "⌫") onBackspace() else onDigit(key)
                        },
                        isBackspace = key == "⌫",
                    )
                }
            }
        }
    }
}