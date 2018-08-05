package com.yury.lebowski.data.local.models

import androidx.room.*
import java.util.*

@Entity(tableName = "Operation")
data class Operation(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val date: Date,
        val currencyType: CurrencyType,
        val operationType: OperationType,
        val amount: Double,
        val amountInUniversal: Double,
        @ForeignKey(entity = Account::class, parentColumns = ["id"], childColumns = ["accountId"])
        val accountId: Long,
        @ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])
        val categoryId: Long
        //@Ignore val category: Category? = null,
       // @Relation(parentColumn = "id", entityColumn = "", entity = Account.class) val account: Account? = null
)