package com.example.nexuspay.feature.transactions.data.repository

import com.example.nexuspay.feature.transactions.data.local.dao.PendingRequestDao
import com.example.nexuspay.feature.transactions.data.local.dao.TransactionDao
import com.example.nexuspay.feature.transactions.data.mapper.toEntity
import com.example.nexuspay.feature.transactions.data.mapper.toListItem
import com.example.nexuspay.feature.transactions.data.mapper.toPendingListItem
import com.example.nexuspay.feature.transactions.data.remote.ITransactionsRemoteDataSource
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import com.example.nexuspay.feature.transactions.domain.repository.ITransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TransactionsRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val pendingRequestDao: PendingRequestDao,
    private val remoteDataSource: ITransactionsRemoteDataSource,
    private val json: Json,
) : ITransactionsRepository {
    override fun observeTransactions(): Flow<List<TransactionListItem>> = combine(
        transactionDao.observeAll(),
        pendingRequestDao.observeByType(PendingRequestType.SEND_TRANSACTION.name),
    ) { confirmed, pending ->
        val pendingItems = pending.map { it.toPendingListItem(json) }
        val confirmedItems = confirmed.map { it.toListItem() }
        (pendingItems + confirmedItems).sortedByDescending { it.date + it.time }
    }

    override suspend fun syncFromRemote(identifier: String) {
        val entities = remoteDataSource.getTransactions(identifier).map { it.toEntity() }
        transactionDao.insertAll(entities)
    }
}