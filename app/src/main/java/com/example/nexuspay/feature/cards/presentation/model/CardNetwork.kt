package com.example.nexuspay.feature.cards.presentation.model

enum class CardNetwork { VISA, MASTERCARD,AMERICAN_EXPRESS }

data class VirtualCardData(
    val cardholderName: String,
    val cardNumber: String,
    val expiryDate: String,
    val network: CardNetwork?,
    val backgroundImageUrl: String? = null,
)