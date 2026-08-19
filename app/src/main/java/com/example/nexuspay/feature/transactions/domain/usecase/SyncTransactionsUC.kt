package com.example.nexuspay.feature.transactions.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.transactions.domain.repository.ITransactionsRepository
import javax.inject.Inject

class SyncTransactionsUC @Inject constructor(
    private val repository: ITransactionsRepository,
) : BaseUseCase<Unit, String>() {
    override suspend fun execute(params: String?) {
        requireNotNull(params) { "A user identifier is required." }
        repository.syncFromRemote(params)
    }
}