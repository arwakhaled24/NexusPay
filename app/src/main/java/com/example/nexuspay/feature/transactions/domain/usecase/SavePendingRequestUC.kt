package com.example.nexuspay.feature.transactions.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.transactions.domain.model.PendingRequest
import com.example.nexuspay.feature.transactions.domain.repository.IPendingRequestRepository
import javax.inject.Inject

class SavePendingRequestUC @Inject constructor(
    private val repository: IPendingRequestRepository,
) : BaseUseCase<Unit, PendingRequest>() {
    override suspend fun execute(params: PendingRequest?) {
        requireNotNull(params) { "A pending request is required." }
        repository.save(params)
    }
}