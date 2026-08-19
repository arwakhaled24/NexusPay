package com.example.nexuspay.navigation

import com.example.nexuspay.feature.transactions.presentation.TransactionsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexuspay.feature.cards.presentation.AddCardScreen
import com.example.nexuspay.design.components.HomeTopBar
import com.example.nexuspay.feature.cards.presentation.CardsScreen
import com.example.nexuspay.feature.home.presentation.HomeScreen
import com.example.nexuspay.feature.send.presentation.SendScreen

@Composable
fun NexusPayNavGraph(
    navController: NavHostController = rememberNavController(),
    navigationViewModel: NavigationViewModel = hiltViewModel(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNav = currentRoute in BottomNavItem.entries.map { it.screen.route }
    val imageUrl by navigationViewModel.imageUrl.collectAsStateWithLifecycle()

    fun navigateToTopLevel(screen: Screen) {
        navController.navigate(screen.route) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        topBar = {
                HomeTopBar(imageUrl)

        },
        bottomBar = {
            if (showBottomNav) {
                NexusPayBottomNavBar(
                    currentRoute = currentRoute,
                    onItemSelected = ::navigateToTopLevel,
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
                HomeScreen(
                    onNavigateToSendMoney = {
                        navController.navigate(Screen.SendMoney.route)
                    },
                    onNavigateToTransactions = {
                        navigateToTopLevel(Screen.Transactions)
                    },
                )
            }
            composable(Screen.Cards.route) {
                CardsScreen(
                    onNavigateToAddCard = {
                        navController.navigate(Screen.AddCard.route)
                    },
                )
            }
            composable(Screen.AddCard.route) {
                AddCardScreen(
                    onNavigateBack = navController::navigateUp,
                )
            }
            composable(Screen.Transactions.route) {
                TransactionsScreen()
            }
            composable(Screen.SendMoney.route) {
                SendScreen(onNavigateBack = navController::navigateUp)
            }
        }
    }
}
