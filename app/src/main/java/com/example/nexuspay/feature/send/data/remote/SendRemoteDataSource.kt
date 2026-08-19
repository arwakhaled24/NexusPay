package com.example.nexuspay.feature.send.data.remote

import com.example.nexuspay.feature.send.data.model.SendContactDto
import retrofit2.http.GET
import javax.inject.Inject

interface SendApi {
    @GET("api/users")
    suspend fun getContacts(): List<SendContactDto>
}

interface ISendRemoteDataSource {
    suspend fun getContacts(): List<SendContactDto>
}

class SendRemoteDataSource @Inject constructor(
    private val api: SendApi,
) : ISendRemoteDataSource {
    override suspend fun getContacts(): List<SendContactDto> = api.getContacts()
}