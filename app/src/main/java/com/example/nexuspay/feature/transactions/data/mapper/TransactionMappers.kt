package com.example.nexuspay.feature.transactions.data.mapper

import com.example.nexuspay.feature.home.data.model.TransactionDto
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.data.local.entity.TransactionEntity
import com.example.nexuspay.feature.transactions.data.model.SendTransactionPayload
import com.example.nexuspay.feature.transactions.domain.model.PendingRequest
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestStatus
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.model.TransactionDisplayStatus
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import kotlinx.serialization.json.Json

fun TransactionDto.toEntity() = TransactionEntity(
    id = id,
    amount = amount,
    currency = currency,
    type = type,
    state = state,
    description = description,
    date = date,
    time = time,
)

fun TransactionEntity.toListItem() = TransactionListItem(
    id = id.toString(),
    amount = amount,
    currency = currency,
    type = type,
    state = state,
    description = description,
    date = date,
    time = time,
    displayStatus = TransactionDisplayStatus.CONFIRMED,
)

fun PendingRequestEntity.toDomain() = PendingRequest(
    localId = localId,
    type = PendingRequestType.valueOf(type),
    payload = payload,
    createdAt = createdAt,
    status = PendingRequestStatus.valueOf(status),
)

fun PendingRequest.toEntity() = PendingRequestEntity(
    localId = localId,
    type = type.name,
    payload = payload,
    createdAt = createdAt,
    status = status.name,
)

fun PendingRequestEntity.toPendingListItem(json: Json): TransactionListItem {
    val payload = json.decodeFromString<SendTransactionPayload>(payload)
    return TransactionListItem(
        id = localId,
        amount = payload.amount,
        currency = payload.currency,
        type = "SENT",
        state = PendingRequestStatus.PENDING.name,
        description = "Sending to ${payload.receiverIdentifier}",
        date = "",
        time = "",
        displayStatus = TransactionDisplayStatus.PENDING,
    )
}