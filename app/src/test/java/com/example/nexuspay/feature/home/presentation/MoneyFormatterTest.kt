package com.example.nexuspay.feature.home.presentation

import com.example.nexuspay.feature.home.domain.model.Money
import org.junit.Assert.assertEquals
import org.junit.Test

class MoneyFormatterTest {

    @Test
    fun formatsPennyValuesAsEgpMajorUnits() {
        assertEquals("EGP 6,340.00", MoneyFormatter.format(Money(634000, "EGP")))
        assertEquals("EGP 75.25", MoneyFormatter.format(Money(7525, "EGP")))
        assertEquals("EGP 1,336.24", MoneyFormatter.format(Money(133624, "EGP")))
    }
}
