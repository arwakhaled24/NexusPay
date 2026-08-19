package com.example.nexuspay.feature.send.data.repository

import com.example.nexuspay.feature.send.data.mapper.SendContactMapper
import com.example.nexuspay.feature.send.data.remote.ISendRemoteDataSource
import com.example.nexuspay.feature.send.domain.model.SendContact
import com.example.nexuspay.feature.send.domain.repository.ISendRepository
import javax.inject.Inject

class SendRepository @Inject constructor(
    private val remoteDataSource: ISendRemoteDataSource,
) : ISendRepository {
    override suspend fun getContacts(): List<SendContact> =
        SendContactMapper.dtoToDomain(remoteDataSource.getContacts())
}