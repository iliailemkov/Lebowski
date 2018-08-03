package com.yury.lebowski.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.models.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM Account") fun getAll() : List<Account>
    @Insert fun insertAll(list: List<Account>)
 //   @Delete fun deleteAll()
}