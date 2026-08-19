package com.example.nexuspay.feature.transactions.domain.model

data class TransactionListItem(
    val id: String,
    val amount: Long,
    val currency: String,
    val type: String,
    val state: String,
    val description: String,
    val date: String,
    val time: String,
    val displayStatus: TransactionDisplayStatus,
)

enum class TransactionDisplayStatus {
    CONFIRMED,
    PENDING,
}