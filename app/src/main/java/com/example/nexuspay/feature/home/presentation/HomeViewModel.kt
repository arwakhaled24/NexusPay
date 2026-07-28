package com.example.nexuspay.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.example.nexuspay.core.data.models.Resource
import com.example.nexuspay.core.viewmodel.NexusPayViewModel
import com.example.nexuspay.feature.home.domain.HOME_USER_IDENTIFIER
import com.example.nexuspay.feature.home.domain.usecase.GetHomeDataUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeData: GetHomeDataUC,
) : NexusPayViewModel<
    HomeContract.HomeActions,
    HomeContract.HomeEvents,
    HomeContract.HomeState,
    >(HomeContract.HomeState()) {

    init {
        loadHomeData()
    }

    override fun onActionTrigger(action: HomeContract.HomeActions) {
        when (action) {
            HomeContract.HomeActions.Retry -> loadHomeData()
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
                is Resource.Success -> setState(
                    oldViewState.copy(
                        isLoading = false,
                        user = result.model.user,
                        recentTransactions = result.model.recentTransactions,
                        exception = null,
                    ),
                )
                is Resource.Failure -> setState(
                    oldViewState.copy(
                        isLoading = false,
                        exception = result.exception,
                    ),
                )
            }
        }
    }
}
