package com.example.nexuspay.feature.transactions.data.remote

import com.example.nexuspay.feature.home.data.model.TransactionDto
import com.example.nexuspay.feature.transactions.data.model.SendTransactionPayload
import javax.inject.Inject


class TransactionsRemoteDataSource @Inject constructor(
    private val api: TransactionsApi,
) : ITransactionsRemoteDataSource {
    override suspend fun getTransactions(identifier: String): List<TransactionDto> =
        api.getTransactions(identifier)

    override suspend fun sendTransaction(payload: SendTransactionPayload, idempotencyKey: String) {
        api.sendTransaction(payload, idempotencyKey)
    }
}

