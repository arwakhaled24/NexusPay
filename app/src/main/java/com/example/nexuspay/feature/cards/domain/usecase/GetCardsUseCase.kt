package com.example.nexuspay.feature.cards.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.cards.domain.repository.CardsRepository
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val repository: CardsRepository,
) : BaseUseCase<List<VirtualCardData>, Unit>() {
    override suspend fun execute(params: Unit?): List<VirtualCardData> = repository.getCards()
}