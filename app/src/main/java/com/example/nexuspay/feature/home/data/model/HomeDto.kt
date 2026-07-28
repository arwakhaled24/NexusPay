package com.example.nexuspay.feature.home.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val name: String,
    val identifier: String,
    val balance: Long,
    val currency: String,
    val avatar: String,
)

@Serializable
data class TransactionDto(
    val id: Long,
    val amount: Long,
    val currency: String,
    val type: String,
    val state: String,
    val description: String,
    val date: String,
    val time: String,
)
