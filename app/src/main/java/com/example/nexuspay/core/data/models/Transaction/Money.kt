package com.example.nexuspay.core.data.models.Transaction

import java.math.BigDecimal

data class Money(
    val minorUnits: Long,
    val currency: String,
) {
    fun toMajorUnits(): BigDecimal = BigDecimal.valueOf(minorUnits).movePointLeft(2)
}