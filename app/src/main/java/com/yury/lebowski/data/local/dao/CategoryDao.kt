package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.enums.OperationType

@Dao
interface CategoryDao {

    @Query("SELECT * FROM Category")
    fun getAll() : LiveData<List<Category>>

    @Query("SELECT * FROM Category WHERE operationType = :operationType")
    fun filterByType(operationType : OperationType) : LiveData<List<Category>>

    @Insert
    fun insertAll(list: List<Category>)
}