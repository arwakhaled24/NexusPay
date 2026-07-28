package com.example.nexuspay.core.data.models

import com.example.nexuspay.core.data.models.exception.NexusPayException

sealed class Resource<out T> {

    data class Success<out T>(val model: T) : Resource<T>()

    data class Failure(val exception: NexusPayException) : Resource<Nothing>()

    data class Progress(val loading: Boolean = true) : Resource<Nothing>()

    companion object {
        fun <T> success(model: T): Resource<T> = Success(model)
        fun failure(exception: NexusPayException): Resource<Nothing> = Failure(exception)
        fun loading(isLoading: Boolean = true): Resource<Nothing> = Progress(isLoading)
    }
}
