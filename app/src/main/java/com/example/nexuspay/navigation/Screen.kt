package com.example.nexuspay.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Cards : Screen("cards")
    data object Transactions : Screen("transactions")
    data object SendMoney : Screen("send_money")
    data object Profile : Screen("profile")
}

enum class BottomNavItem(
    val screen: Screen,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    Home(
        screen = Screen.Home,
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    Cards(
        screen = Screen.Cards,
        label = "Cards",
        selectedIcon = Icons.Filled.CreditCard,
        unselectedIcon = Icons.Outlined.CreditCard,
    ),
    Transactions(
        screen = Screen.Transactions,
        label = "Transactions",
        selectedIcon = Icons.Filled.Receipt,
        unselectedIcon = Icons.Outlined.Receipt,
    ),
}
