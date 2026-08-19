package com.example.nexuspay.feature.transactions.domain.repository

import com.example.nexuspay.feature.transactions.domain.model.PendingRequest
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import kotlinx.coroutines.flow.Flow

interface IPendingRequestRepository {
    suspend fun save(request: PendingRequest)
    suspend fun delete(localId: String)
    fun observeByType(type: String): Flow<List<PendingRequestEntity>>
}