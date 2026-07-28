package com.example.nexuspay.feature.home.presentation

import com.example.nexuspay.core.data.models.exception.NexusPayException
import com.example.nexuspay.core.viewmodel.ViewAction
import com.example.nexuspay.core.viewmodel.ViewEvent
import com.example.nexuspay.core.viewmodel.ViewState
import com.example.nexuspay.feature.home.domain.model.Transaction
import com.example.nexuspay.feature.home.domain.model.User

interface HomeContract {

    sealed class HomeActions : ViewAction {
        data object Retry : HomeActions()
        data object OnSendMoneyClick : HomeActions()
    }

    sealed class HomeEvents : ViewEvent {
        data object NavigateToSendMoney : HomeEvents()
    }

    data class HomeState(
        val isLoading: Boolean = false,
        val user: User? = null,
        val recentTransactions: List<Transaction> = emptyList(),
        val exception: NexusPayException? = null,
    ) : ViewState
}
