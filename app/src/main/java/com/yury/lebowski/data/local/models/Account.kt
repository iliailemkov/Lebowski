package com.yury.lebowski.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yury.lebowski.data.local.models.enums.CurrencyType


@Entity(tableName = "Account")
data class Account(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        val name: String,
        val balance: Double,
        val currencyType: CurrencyType
) : SpinnerObject {
    override fun getTitle() = name
}