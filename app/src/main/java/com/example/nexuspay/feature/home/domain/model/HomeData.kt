package com.example.nexuspay.feature.home.domain.model

data class HomeData(
    val user: User,
    val recentTransactions: List<Transaction>,
)

data class User(
    val id: Long,
    val name: String,
    val identifier: String,
    val balance: Money,
    val avatarUrl: String,
)

data class Transaction(
    val id: Long,
    val amount: Money,
    val type: TransactionType,
    val state: TransactionState,
    val description: String,
    val date: String,
    val time: String,
)

enum class TransactionType {
    SENT,
    RECEIVED,
    UNKNOWN,
}

enum class TransactionState {
    COMPLETED,
    PENDING,
    UNKNOWN,
}
