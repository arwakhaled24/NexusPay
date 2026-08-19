package com.example.nexuspay.feature.cards.domain.repository

import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    suspend fun getCards(): List<VirtualCardData>
    fun observeCards(): Flow<List<VirtualCardData>>
    suspend fun addCard(card: VirtualCardData)
}