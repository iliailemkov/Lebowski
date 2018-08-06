package com.yury.lebowski.data.local.models

import androidx.room.*
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationType
import java.util.*

@Entity(tableName = "Operation")
data class Operation(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        val date: Date,
        val operationType: OperationType,
        val amount: Double,
        @ForeignKey(entity = Account::class, parentColumns = ["id"], childColumns = ["accountId"])
        val accountId: Long,
        @ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])
        val categoryId: Long
)