package com.example.nexuspay.feature.home.presentation

import com.example.nexuspay.core.data.models.exception.NexusPayException
import com.example.nexuspay.core.viewmodel.ViewAction
import com.example.nexuspay.core.viewmodel.ViewEvent
import com.example.nexuspay.core.viewmodel.ViewState
import com.example.nexuspay.feature.home.domain.model.User
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem

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
        val recentTransactions: List<TransactionListItem> = emptyList(),
        val pendingRequests: List<PendingRequestEntity> = emptyList(),
        val exception: NexusPayException? = null,
    ) : ViewState
}
