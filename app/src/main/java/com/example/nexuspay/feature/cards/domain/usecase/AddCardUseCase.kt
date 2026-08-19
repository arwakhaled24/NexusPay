package com.example.nexuspay.feature.cards.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.cards.domain.repository.CardsRepository
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
import javax.inject.Inject

class AddCardUseCase @Inject constructor(
    private val repository: CardsRepository,
) : BaseUseCase<Unit, VirtualCardData>() {
    override suspend fun execute(params: VirtualCardData?) {
        requireNotNull(params) { "A card is required." }
        repository.addCard(params)
    }
}