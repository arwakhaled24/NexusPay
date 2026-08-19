package com.example.nexuspay.feature.home.presentation.composable


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.NexusPayPrimary
import com.example.nexuspay.feature.home.domain.model.User

@Composable
 fun HomeTopBar(user: User) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HomeTopBarHeight),
        color = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.HomeHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "${user.name} profile image",
                modifier = Modifier
                    .size(Dimens.IconSizeXl - Dimens.SpacingSm)
                    .clip(CircleShape),
            )
            Spacer(Modifier.width(Dimens.SpacingMd))
            Text(
                text = "NexusPay",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.headlineLarge,
                color = NexusPayPrimary,
            )
            Icon(
                imageVector = Icons.Filled.NotificationsNone,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}