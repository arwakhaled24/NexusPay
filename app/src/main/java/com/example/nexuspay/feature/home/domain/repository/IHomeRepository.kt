package com.example.nexuspay.feature.home.domain.repository

import com.example.nexuspay.feature.home.domain.model.HomeData

interface IHomeRepository {
    suspend fun getHomeData(identifier: String): HomeData
}
