package com.example.nexuspay.feature.home.presentation

import com.example.nexuspay.feature.home.domain.model.Money
import java.text.NumberFormat
import java.util.Locale

object MoneyFormatter {
    private val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    fun format(money: Money): String =
        "${money.currency} ${formatter.format(money.toMajorUnits())}"
}
