package com.example.nexuspay.feature.transactions.domain.repository

import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import kotlinx.coroutines.flow.Flow

interface ITransactionsRepository {
    fun observeTransactions(): Flow<List<TransactionListItem>>
    suspend fun syncFromRemote(identifier: String)
}