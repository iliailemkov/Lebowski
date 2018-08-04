package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.local.models.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM Account") fun getAll() : LiveData<List<Account>>
    @Query("SELECT * FROM Account WHERE id = :id") fun findById(id: Long) : LiveData<Account>
    @Insert fun insertAll(list: List<Account>)
 //   @Delete fun deleteAll()
}