package com.example.nexuspay.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.example.nexuspay.core.data.models.Transaction.Resource
import com.example.nexuspay.core.viewmodel.NexusPayViewModel
import com.example.nexuspay.feature.home.domain.HOME_USER_IDENTIFIER
import com.example.nexuspay.feature.home.domain.usecase.GetHomeDataUC
import com.example.nexuspay.feature.transactions.domain.repository.IPendingRequestRepository
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.usecase.ObserveTransactionsUC
import com.example.nexuspay.feature.transactions.domain.usecase.SyncTransactionsUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeData: GetHomeDataUC,
    private val userRepository: IUserRepository,
    private val pendingRequestRepository: IPendingRequestRepository,
    private val observeTransactions: ObserveTransactionsUC,
    private val syncTransactions: SyncTransactionsUC,
) : NexusPayViewModel<
    HomeContract.HomeActions,
    HomeContract.HomeEvents,
    HomeContract.HomeState,
    >(HomeContract.HomeState()) {

    init {
        loadHomeData()
        observeTransactions()
        syncTransactions()
        observePendingRequests()
    }

    override fun onActionTrigger(action: HomeContract.HomeActions) {
        when (action) {
            HomeContract.HomeActions.Retry -> {
                loadHomeData()
                syncTransactions()
            }
            HomeContract.HomeActions.OnSendMoneyClick -> {
                sendEvent(HomeContract.HomeEvents.NavigateToSendMoney)
            }
        }
    }

    override fun clearState() {
        setState(HomeContract.HomeState())
    }

    private fun loadHomeData() {
        getHomeData(viewModelScope, HOME_USER_IDENTIFIER) { result ->
            when (result) {
                is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                is Resource.Success -> {
                    val imageUrl = result.model.user.avatarUrl
                    if (imageUrl.isNotBlank()) {
                        viewModelScope.launch {
                            userRepository.saveImageUrl(imageUrl)
                        }
                    }
                    setState(
                        oldViewState.copy(
                            isLoading = false,
                            user = result.model.user,
                            exception = null,
                        ),
                    )
                }
                is Resource.Failure -> setState(
                    oldViewState.copy(
                        isLoading = false,
                        exception = result.exception,
                    ),
                )
            }
        }
    }

    private fun observePendingRequests() {
        viewModelScope.launch {
            pendingRequestRepository.observeByType(PendingRequestType.SEND_TRANSACTION.name)
                .collectLatest { pendingRequests ->
                setState(oldViewState.copy(pendingRequests = pendingRequests))
            }
        }
    }

    private fun observeTransactions() {
        viewModelScope.launch {
            observeTransactions.execute().collectLatest { transactions ->
                setState(oldViewState.copy(recentTransactions = transactions.take(5)))
            }
        }
    }

    private fun syncTransactions() {
        viewModelScope.launch {
            syncTransactions(viewModelScope, userRepository.getIdentifier()) { result ->
                when (result) {
                    is Resource.Progress -> setState(oldViewState.copy(isLoading = result.loading))
                    is Resource.Success -> setState(oldViewState.copy(isLoading = false, exception = null))
                    is Resource.Failure -> setState(
                        oldViewState.copy(isLoading = false, exception = result.exception),
                    )
                }
            }
        }
    }
}
