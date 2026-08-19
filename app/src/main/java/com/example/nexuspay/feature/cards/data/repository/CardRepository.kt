package com.example.nexuspay.feature.cards.data.repository

import com.example.nexuspay.feature.cards.data.local.dao.CardDao
import com.example.nexuspay.feature.cards.data.mapper.toEntity
import com.example.nexuspay.feature.cards.data.mapper.toVirtualCardData
import com.example.nexuspay.feature.cards.domain.repository.CardsRepository
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CardRepository @Inject constructor(
    private val cardDao: CardDao,
) : CardsRepository {
    override suspend fun getCards(): List<VirtualCardData> =
        cardDao.getAll().map { it.toVirtualCardData() }

    override fun observeCards(): Flow<List<VirtualCardData>> =
        cardDao.observeAll().map { entities -> entities.map { it.toVirtualCardData() } }

    override suspend fun addCard(card: VirtualCardData) {
        cardDao.insert(card.toEntity())
    }
}