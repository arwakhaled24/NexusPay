package com.example.nexuspay.feature.transactions.data.remote

import com.example.nexuspay.feature.home.data.model.TransactionDto
import com.example.nexuspay.feature.transactions.data.model.SendTransactionPayload

interface ITransactionsRemoteDataSource {
    suspend fun getTransactions(identifier: String): List<TransactionDto>
    suspend fun sendTransaction(payload: SendTransactionPayload, idempotencyKey: String)
}