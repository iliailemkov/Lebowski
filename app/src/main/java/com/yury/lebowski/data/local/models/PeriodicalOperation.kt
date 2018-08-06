package com.yury.lebowski.data.local.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "PeriodicalOperation")
data class PeriodicalOperation(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,
        @ForeignKey(entity = Operation::class, parentColumns = ["id"], childColumns = ["operationId"])
        val operationId: Long,
        val period: Long
)