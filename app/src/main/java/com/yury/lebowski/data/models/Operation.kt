package com.yury.lebowski.data.models

import androidx.room.*
import java.util.*

@Entity(tableName = "Operation")
data class Operation(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val date: Date,
        val currencyType: CurrencyType,
        val amount: Double,
        val amountInUniversal: Double,
        @ForeignKey(entity = Account::class, parentColumns = ["id"], childColumns = ["accountId"])
        val accountId: Long
        //@Ignore val category: Category? = null,
       // @Relation(parentColumn = "id", entityColumn = "", entity = Account.class) val account: Account? = null
)