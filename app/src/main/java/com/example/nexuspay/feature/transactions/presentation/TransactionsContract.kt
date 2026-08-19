package com.example.nexuspay.feature.transactions.presentation

import com.example.nexuspay.core.data.models.exception.NexusPayException
import com.example.nexuspay.core.viewmodel.ViewAction
import com.example.nexuspay.core.viewmodel.ViewEvent
import com.example.nexuspay.core.viewmodel.ViewState
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem

interface TransactionsContract {
    sealed class Actions : ViewAction {
        data object Retry : Actions()
        data class SelectTransaction(val transaction: TransactionListItem) : Actions()
        data object DismissReceiptActions : Actions()
        data object ShareSelectedReceipt : Actions()
        data object DownloadSelectedReceipt : Actions()
    }
    sealed class Events : ViewEvent {
        data class ShowToast(val message: String) : Events()
        data class ShareReceipt(val uri: android.net.Uri) : Events()
    }
    data class State(
        val isLoading: Boolean = false,
        val transactions: List<TransactionListItem> = emptyList(),
        val pendingRequests: List<PendingRequestEntity> = emptyList(),
        val selectedTransaction: TransactionListItem? = null,
        val exception: NexusPayException? = null,
    ) : ViewState
}