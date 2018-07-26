package com.yury.lebowski

import com.yury.lebowski.models.CurrencyType
import com.yury.lebowski.models.OperationType
import com.yury.lebowski.models.Record
import com.yury.lebowski.repository.BalanceRepository
import com.yury.lebowski.repository.convertFromUniversal
import com.yury.lebowski.service.calculateBalanceAfterOperations
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun convertToSecondaryCurrency_isCorrect() {
        val mainCurrency = BalanceRepository().getAmountInUniversal()
        val secondartCurrency = mainCurrency * CurrencyType.Ruble.convertFromUniversal()
        assertEquals(1015.2, secondartCurrency, 0.01)
    }

    @Test
    fun simpleRecordIncomeDollar_isCorrect() {
        val startBalance: Double = 10.toDouble()
        val endBalance = calculateBalanceAfterOperations(startBalance, listOf(Record(OperationType.Income, 1.0, CurrencyType.Dollar)))
        assertEquals(11.0, endBalance, 0.01)
    }

    @Test
    fun simpleRecordExpenditureDollar_isCorrect() {
        val startBalance: Double = 10.toDouble()
        val endBalance = calculateBalanceAfterOperations(startBalance, listOf(Record(OperationType.Expenditure, 1.0, CurrencyType.Dollar)))
        assertEquals(9.0, endBalance, 0.01)
    }

    @Test
    fun simpleRecordIncomeRuble_isCorrect() {
        val startBalance: Double = 10.toDouble()
        val endBalance = calculateBalanceAfterOperations(startBalance, listOf(Record(OperationType.Income, 63.45, CurrencyType.Ruble)))
        assertEquals(11.0, endBalance, 0.01)
    }

    @Test
    fun simpleRecordExpenditureRuble_isCorrect() {
        val startBalance: Double = 10.toDouble()
        val endBalance = calculateBalanceAfterOperations(startBalance, listOf(Record(OperationType.Expenditure, 63.45, CurrencyType.Ruble)))
        assertEquals(9.0, endBalance, 0.01)
    }

    @Test
    fun manyRecords_isCorrect() {
        val startBalance: Double = 10.toDouble()
        val endBalance = calculateBalanceAfterOperations(startBalance, listOf(
                Record(OperationType.Expenditure, 63.45 * 9, CurrencyType.Ruble),
                Record(OperationType.Income, 19.0, CurrencyType.Dollar),
                Record(OperationType.Income, 63.45 * 40, CurrencyType.Ruble)))
        assertEquals(60.0, endBalance, 0.01)
    }
}
