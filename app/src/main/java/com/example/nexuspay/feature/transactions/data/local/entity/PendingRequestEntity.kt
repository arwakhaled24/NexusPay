package com.example.nexuspay.feature.transactions.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_requests")
data class PendingRequestEntity(
    @PrimaryKey val localId: String,
    val type: String,
    val payload: String,
    val createdAt: Long,
    val status: String = "PENDING",
)