package com.yury.lebowski.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.local.models.ExchangeRate
import com.yury.lebowski.data.local.models.enums.CurrencyType

@Dao
interface ExchangeRateDao {

    @Query("SELECT rate FROM ExchangeRate WHERE currencyFrom = :currencyFrom AND currencyTo = :currencyTo" )
    fun findByCurrency(currencyFrom: CurrencyType, currencyTo: CurrencyType) : Double

    @Query("SELECT * FROM ExchangeRate")
    fun getAll() : List<ExchangeRate>

    @Insert
    fun insertAll(list: List<ExchangeRate>)
}