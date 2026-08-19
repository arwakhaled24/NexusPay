package com.example.nexuspay.feature.transactions.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import com.example.nexuspay.feature.transactions.domain.repository.ITransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTransactionsUC @Inject constructor(
    private val repository: ITransactionsRepository,
) : BaseUseCase<Flow<List<TransactionListItem>>, Unit>() {
    override suspend fun execute(params: Unit?): Flow<List<TransactionListItem>> =
        repository.observeTransactions()
}