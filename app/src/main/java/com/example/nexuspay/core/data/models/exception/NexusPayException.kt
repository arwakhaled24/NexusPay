package com.example.nexuspay.core.data.models.exception

data class NexusPayException(
    override val message: String,
    val code: Int? = null,
    override val cause: Throwable? = null,
) : Exception(message, cause)
