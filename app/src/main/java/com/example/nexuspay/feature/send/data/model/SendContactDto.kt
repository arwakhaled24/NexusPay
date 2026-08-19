package com.example.nexuspay.feature.send.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SendContactDto(
    val id: Long,
    val identifier: String,
    val name: String,
    val avatar: String,
)