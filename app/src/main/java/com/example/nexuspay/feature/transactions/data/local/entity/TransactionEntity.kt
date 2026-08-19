package com.example.nexuspay.feature.transactions.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: Long,
    val amount: Long,
    val currency: String,
    val type: String,
    val state: String,
    val description: String,
    val date: String,
    val time: String,
)