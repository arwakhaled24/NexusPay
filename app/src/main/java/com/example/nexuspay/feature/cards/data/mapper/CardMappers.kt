package com.example.nexuspay.feature.cards.data.mapper

import com.example.nexuspay.feature.cards.data.local.entity.CardEntity
import com.example.nexuspay.feature.cards.presentation.model.CardNetwork
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData

fun VirtualCardData.toEntity(): CardEntity = CardEntity(
    cardNumber = cardNumber,
    cardholderName = cardholderName,
    expiryDate = expiryDate,
    network = requireNotNull(network).name,
    backgroundImageUrl = backgroundImageUrl,
)

fun CardEntity.toVirtualCardData(): VirtualCardData = VirtualCardData(
    cardNumber = cardNumber,
    cardholderName = cardholderName,
    expiryDate = expiryDate,
    network = CardNetwork.valueOf(network),
    backgroundImageUrl = backgroundImageUrl,
)