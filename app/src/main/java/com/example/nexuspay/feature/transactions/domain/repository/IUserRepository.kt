package com.example.nexuspay.feature.transactions.domain.repository

interface IUserRepository {
    suspend fun getIdentifier(): String
    suspend fun saveIdentifier(value: String)
    suspend fun getImageUrl(): String
    suspend fun saveImageUrl(value: String)
}