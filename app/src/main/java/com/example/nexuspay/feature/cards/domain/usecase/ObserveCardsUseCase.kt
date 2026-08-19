package com.example.nexuspay.feature.cards.domain.usecase

import com.example.nexuspay.feature.cards.domain.repository.CardsRepository
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCardsUseCase @Inject constructor(
    private val repository: CardsRepository,
) {
    operator fun invoke(): Flow<List<VirtualCardData>> =
        repository.observeCards()
}