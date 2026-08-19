package com.example.nexuspay.feature.home.presentation.composable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.GlassSurface
import com.example.nexuspay.design.theme.NexusPayPrimary
@Composable
 fun SendMoneyButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(Dimens.QuickActionWidth)
            .height(Dimens.QuickActionHeight),
        shape = CircleShape,
        border = BorderStroke(Dimens.SpacingXxs / 2, NexusPayPrimary.copy(alpha = 0.45f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = GlassSurface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        contentPadding = PaddingValues(horizontal = Dimens.SpacingLg),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send money",
            tint = NexusPayPrimary,
        )
        Spacer(Modifier.width(Dimens.SpacingMd))
        Text(text = "Send", style = MaterialTheme.typography.labelLarge)
    }
}
