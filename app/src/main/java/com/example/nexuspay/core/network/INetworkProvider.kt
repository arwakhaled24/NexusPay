package com.example.nexuspay.core.network

import retrofit2.http.GET

interface INetworkProvider {
    // Placeholder endpoint — Retrofit requires at least one annotated method.
    // Feature-specific endpoints will be added as the app grows.
    @GET("health")
    suspend fun healthCheck()
}
