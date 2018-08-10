package com.yury.lebowski.data.local.converters

import com.yury.lebowski.data.local.models.enums.CurrencyType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertEquals


@RunWith(JUnit4::class)
class CurrencyTypeConverterTest {

    val currencyTypeConverter = CurrencyTypeConverter()

    @Test
    fun testCurrencyTypeToString() {
        assertEquals(currencyTypeConverter.codeToString(CurrencyType.Dollar), "USD")
    }

    @Test
    fun testStringToCurrencyType() {
        assertEquals(currencyTypeConverter.fromString("RUB"), CurrencyType.Ruble)
    }

}