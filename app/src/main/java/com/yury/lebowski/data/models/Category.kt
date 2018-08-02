package com.yury.lebowski.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var operationType: OperationType,
        var nameResourceId: Int
)