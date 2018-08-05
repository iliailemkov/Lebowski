package com.yury.lebowski.data.local.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Account")
data class Account(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val nameResourceId: Int,
        val currentBalanceInUniversal: Double,
        val currencyType: CurrencyType
)