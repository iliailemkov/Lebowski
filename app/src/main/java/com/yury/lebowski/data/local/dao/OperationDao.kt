package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.PeriodicalOperation

@Dao
interface OperationDao {

    @Query("SELECT * FROM Operation")
    fun getAll() : LiveData<List<Operation>>

    @Query("SELECT * FROM Operation WHERE id = :id")
    fun findById(id: Long) : Operation

    @Query("SELECT * FROM Operation WHERE accountId = :id")
    fun findByAccountId(id: Long) : LiveData<List<Operation>>

    @Insert
    fun insertAll(list: List<Operation>)
}