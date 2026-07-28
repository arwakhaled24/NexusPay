package com.example.nexuspay.feature.home.data.remote

import com.example.nexuspay.feature.home.data.model.TransactionDto
import com.example.nexuspay.feature.home.data.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface HomeApi {
    @GET("api/users/{identifier}")
    suspend fun getUser(@Path("identifier") identifier: String): UserDto

    @GET("api/transactions/{identifier}")
    suspend fun getTransactions(@Path("identifier") identifier: String): List<TransactionDto>
}

interface IHomeRemoteDataSource {
    suspend fun getUser(identifier: String): UserDto
    suspend fun getTransactions(identifier: String): List<TransactionDto>
}

class HomeRemoteDataSource @Inject constructor(
    private val api: HomeApi,
) : IHomeRemoteDataSource {
    override suspend fun getUser(identifier: String): UserDto = api.getUser(identifier)

    override suspend fun getTransactions(identifier: String): List<TransactionDto> =
        api.getTransactions(identifier)
}
