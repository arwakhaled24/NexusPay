package com.example.nexuspay.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nexuspay.design.theme.DarkOnBackground
import com.example.nexuspay.design.theme.Dimens
import com.example.nexuspay.design.theme.GlassOutline
import com.example.nexuspay.design.theme.GlassTopBar
import com.example.nexuspay.design.theme.NavigationSelectedBackground
import com.example.nexuspay.design.theme.SelectedNavigationContent

@Composable
fun NexusPayBottomNavBar(
    currentRoute: String?,
    onItemSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.navigationBarsPadding(),
        color = GlassTopBar,
        border = BorderStroke(Dimens.SpacingXxs / 2, GlassOutline),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = Dimens.HomeBottomNavHeight)
                .padding(
                    horizontal = Dimens.SpacingSm,
                    vertical = Dimens.SpacingSm,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomNavItem.entries.forEach { item ->
                val selected = currentRoute == item.screen.route
                BottomNavItemContent(
                    item = item,
                    selected = selected,
                    onClick = { onItemSelected(item.screen) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemContent(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (selected) SelectedNavigationContent else DarkOnBackground
    val icon = if (selected) item.selectedIcon else item.unselectedIcon

    val itemContent: @Composable () -> Unit = {
        Column(
            modifier = Modifier.padding(
                horizontal = Dimens.SpacingMd,
                vertical = Dimens.SpacingXs,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = item.label,
                modifier = Modifier.size(Dimens.IconSizeMd),
                tint = contentColor,
            )
            Spacer(Modifier.height(Dimens.SpacingXs))
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor,
            )
        }
    }

    if (selected) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                onClick = onClick,
                modifier = Modifier.widthIn(min = Dimens.NavigationItemMinWidth),
                shape = RoundedCornerShape(Dimens.NavigationSelectedHeight / 2),
                color = NavigationSelectedBackground,
            ) {
                itemContent()
            }
        }
    } else {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                onClick = onClick,
                modifier = Modifier.widthIn(min = Dimens.NavigationItemMinWidth),
                color = Color.Transparent,
            ) {
                itemContent()
            }
        }
    }
}
