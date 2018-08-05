package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category") fun getAll() : LiveData<List<Category>>
    @Insert fun insertAll(list: List<Category>)
   // @Delete fun deleteAll()
}