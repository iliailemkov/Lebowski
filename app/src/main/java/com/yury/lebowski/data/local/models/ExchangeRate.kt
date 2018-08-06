package com.yury.lebowski.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yury.lebowski.data.local.models.enums.CurrencyType

@Entity(tableName = "ExchangeRate")
data class ExchangeRate(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        val currencyfrom: CurrencyType,
        val currencyTo: CurrencyType,
        val rate: Double
)