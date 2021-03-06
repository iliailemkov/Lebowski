package com.yury.lebowski.data.local.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import java.util.*

@Entity(tableName = "Operation")
data class Operation(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        val date: Date,
        val operationType: OperationType,
        val operationState: OperationState,
        val amount: Double,
        @ForeignKey(entity = Account::class, parentColumns = ["id"], childColumns = ["accountId"], onDelete = ForeignKey.CASCADE)
        val accountId: Long,
        @ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])
        val categoryId: Long
)