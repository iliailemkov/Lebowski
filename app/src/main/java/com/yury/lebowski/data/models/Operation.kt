package com.yury.lebowski.data.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Operation")
data class Operation(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val date: Date,
        val currencyType: CurrencyType,
        val amount: Double,
        val amountInUniversal: Double/*,
        @Ignore val category: Category? = null,
        @Ignore val account: Account? = null*/
)