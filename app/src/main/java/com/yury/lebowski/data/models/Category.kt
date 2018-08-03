package com.yury.lebowski.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val operationType: OperationType,
        val nameResourceId: Int
        //@ForeignKey(entity = Operation::class, parentColumns = ["id"], childColumns = ["operationId"])
        //val operationId: Long
)