package com.yury.lebowski

import com.yury.lebowski.models.CurrencyType
import com.yury.lebowski.repository.BalanceRepository
import com.yury.lebowski.repository.RateRepository
import org.junit.Assert
import org.junit.Test

private const val delta = 0.01

class RateUnitTest {
    @Test
    fun convertToSecondaryCurrencyOffline_isCorrect() {
        val mainCurrency = BalanceRepository().getAmountInUniversal()
        val secondaryCurrency = mainCurrency * RateRepository().getLastOfflineRate(CurrencyType.Ruble)
        Assert.assertEquals(1015.2, secondaryCurrency, delta)
    }


}