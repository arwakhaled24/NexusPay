package com.example.nexuspay.design.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.nexuspay.design.theme.NexusPayPrimary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import coil.compose.AsyncImage
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.feature.home.domain.model.User

@Composable
fun HomeTopBar(image: String) {
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
                model = image,
                contentDescription = " profile image",
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

/*

@Composable
fun TopAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(73.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(0.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0x1AFFFFFF), CircleShape)
                    .background(Color.Transparent)
            ) {

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "NexusPay",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NexusPayPrimary
                    )
                )
            }
        }
    }
}*/
