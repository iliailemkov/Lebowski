package com.yury.lebowski.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yury.lebowski.data.models.Operation

@Dao
interface OperationDao {
    @Query("SELECT * FROM Operation") fun getAll() : LiveData<List<Operation>>
    @Query("SELECT * FROM Operation WHERE accountId = :id") fun findByAccountId(id: Long) : LiveData<List<Operation>>
    // @Delete fun deleteAll()
}