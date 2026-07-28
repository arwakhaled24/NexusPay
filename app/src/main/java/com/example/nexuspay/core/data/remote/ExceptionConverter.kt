package com.example.nexuspay.core.data.remote

import com.example.nexuspay.core.data.models.exception.NexusPayException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionConverter {

    fun convert(throwable: Throwable): NexusPayException {
        return when (throwable) {
            is NexusPayException -> throwable
            is HttpException -> handleHttpException(throwable)
            is UnknownHostException -> NexusPayException(
                message = "No internet connection",
                code = null,
                cause = throwable,
            )
            is SocketTimeoutException -> NexusPayException(
                message = "Connection timed out",
                code = null,
                cause = throwable,
            )
            is IOException -> NexusPayException(
                message = "Network error occurred",
                code = null,
                cause = throwable,
            )
            else -> NexusPayException(
                message = throwable.message ?: "An unexpected error occurred",
                code = null,
                cause = throwable,
            )
        }
    }

    private fun handleHttpException(exception: HttpException): NexusPayException {
        val code = exception.code()
        val message = when (code) {
            401 -> "Unauthorized"
            403 -> "Forbidden"
            404 -> "Not found"
            408 -> "Request timeout"
            500 -> "Internal server error"
            503 -> "Service unavailable"
            else -> exception.message() ?: "HTTP error $code"
        }
        return NexusPayException(message = message, code = code, cause = exception)
    }
}
