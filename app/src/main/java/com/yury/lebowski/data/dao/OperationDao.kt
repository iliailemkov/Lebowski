package com.yury.lebowski.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.yury.lebowski.data.models.Operation

@Dao
interface OperationDao {
    @Query("SELECT * FROM Operation") fun getAll() : List<Operation>
    // @Delete fun deleteAll()
}