package com.example.nexuspay.feature.cards.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cards")
data class CardEntity(
    @PrimaryKey val cardNumber: String,
    val cardholderName: String,
    val expiryDate: String,
    val network: String,
    val backgroundImageUrl: String?,
)