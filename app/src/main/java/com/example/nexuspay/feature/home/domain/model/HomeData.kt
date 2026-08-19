package com.example.nexuspay.feature.home.domain.model

import com.example.nexuspay.core.data.models.Transaction.Money
import com.example.nexuspay.core.data.models.Transaction.Transaction

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




