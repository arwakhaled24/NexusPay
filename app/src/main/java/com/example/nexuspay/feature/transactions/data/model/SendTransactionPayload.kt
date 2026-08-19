package com.example.nexuspay.feature.transactions.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SendTransactionPayload(
    val senderIdentifier: String,
    val receiverIdentifier: String,
    val amount: Long,
    val currency: String,
    val title: String,
)