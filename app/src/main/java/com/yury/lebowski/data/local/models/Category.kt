package com.yury.lebowski.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yury.lebowski.data.local.models.enums.OperationType

@Entity(tableName = "Category")
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        val operationType: OperationType,
        val name: String
)