package com.yury.lebowski.data.models

import android.location.Location
import java.time.LocalDateTime

data class Operation(
        val date: LocalDateTime,
        val location: Location,
        val currencyType: CurrencyType,
        val amount: Double,
        val amountInUniversal: Double,
        val category: Category,
        val account: Account
)