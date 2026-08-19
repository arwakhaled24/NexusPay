package com.example.nexuspay.feature.transactions.data.repository

import com.example.nexuspay.core.local.UserDataStore
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataStore: UserDataStore,
) : IUserRepository {
    override suspend fun getIdentifier(): String = dataStore.getIdentifier()

    override suspend fun saveIdentifier(value: String) {
        dataStore.saveIdentifier(value)
    }

    override suspend fun getImageUrl(): String = dataStore.getImageUrl()

    override suspend fun saveImageUrl(value: String) {
        dataStore.saveImageUrl(value)
    }
}