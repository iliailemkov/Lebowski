package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.local.models.Operation
import java.util.*

@Dao
interface OperationDao {

    @Insert
    fun insertOperation(operation: Operation)

    @Query("SELECT * FROM Operation")
    fun getAll(): LiveData<List<Operation>>

    @Query("SELECT * FROM Operation WHERE id = :id")
    fun findById(id: Long): Operation

    @Query("SELECT * FROM Operation WHERE accountId = :id")
    fun findByAccountId(id: Long): LiveData<List<Operation>>

    @Query("SELECT * FROM Operation WHERE accountId = :id AND date > :startDate AND date < :finishDate")
    fun filterByPeriodAndAccountId(startDate: Date?, finishDate: Date?, id: Long): LiveData<List<Operation>>

    @Query("DELETE FROM Operation WHERE :id = id")
    fun delete(id: Long)

    @Insert
    fun insertAll(list: List<Operation>)
}