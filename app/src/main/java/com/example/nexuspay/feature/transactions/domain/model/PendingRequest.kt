package com.example.nexuspay.feature.transactions.domain.model

data class PendingRequest(
    val localId: String,
    val type: PendingRequestType,
    val payload: String,
    val createdAt: Long,
    val status: PendingRequestStatus = PendingRequestStatus.PENDING,
)