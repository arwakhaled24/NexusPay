package com.example.nexuspay.feature.send.domain.repository

import com.example.nexuspay.feature.send.domain.model.SendContact

interface ISendRepository {
    suspend fun getContacts(): List<SendContact>
}