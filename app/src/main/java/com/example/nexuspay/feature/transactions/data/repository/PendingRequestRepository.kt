package com.example.nexuspay.feature.transactions.data.repository

import com.example.nexuspay.feature.transactions.data.local.dao.PendingRequestDao
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.data.mapper.toEntity
import com.example.nexuspay.feature.transactions.domain.model.PendingRequest
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.repository.IPendingRequestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PendingRequestRepository @Inject constructor(
    private val pendingRequestDao: PendingRequestDao,
) : IPendingRequestRepository {
    override suspend fun save(request: PendingRequest) {
        pendingRequestDao.insert(request.toEntity())
    }

    override suspend fun delete(localId: String) {
        pendingRequestDao.delete(localId)
    }

    override fun observeByType(type: String): Flow<List<PendingRequestEntity>> =
        pendingRequestDao.observeByType(type)
}