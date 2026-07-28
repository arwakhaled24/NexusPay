package com.example.nexuspay.feature.home.domain.model

import java.math.BigDecimal

data class Money(
    val minorUnits: Long,
    val currency: String,
) {
    fun toMajorUnits(): BigDecimal = BigDecimal.valueOf(minorUnits).movePointLeft(2)
}
