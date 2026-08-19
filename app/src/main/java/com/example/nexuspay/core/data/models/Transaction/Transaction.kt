package com.example.nexuspay.core.data.models.Transaction

import com.example.nexuspay.core.data.models.Transaction.TransactionState
import com.example.nexuspay.core.data.models.Transaction.TransactionType

data class Transaction(
    val id: Long,
    val amount: Money,
    val type: TransactionType,
    val state: TransactionState,
    val description: String,
    val date: String,
    val time: String,
)