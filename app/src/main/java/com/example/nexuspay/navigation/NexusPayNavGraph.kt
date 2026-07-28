package com.example.nexuspay.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nexuspay.feature.cards.presentation.CardsScreen
import com.example.nexuspay.feature.home.presentation.HomeScreen
import com.example.nexuspay.feature.transactions.presentation.TransactionsScreen

@Composable
fun NexusPayNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNav = currentRoute in BottomNavItem.entries.map { it.screen.route }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NexusPayBottomNavBar(
                    currentRoute = currentRoute,
                    onItemSelected = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Cards.route) {
                CardsScreen()
            }
            composable(Screen.Transactions.route) {
                TransactionsScreen()
            }
        }
    }
}
