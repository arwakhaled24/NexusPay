package com.example.nexuspay.feature.transactions.data.remote
import com.example.nexuspay.feature.home.data.model.TransactionDto
import com.example.nexuspay.feature.transactions.data.model.SendTransactionPayload
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
interface TransactionsApi {
    @GET("api/transactions/{identifier}")
    suspend fun getTransactions(@Path("identifier") identifier: String): List<TransactionDto>

    @POST("api/transactions/send")
    suspend fun sendTransaction(
        @Body payload: SendTransactionPayload,
        @Header("X-Idempotency-Key") idempotencyKey: String,
    )
}
