package com.yury.lebowski.data.dao

import androidx.room.Delete
import androidx.room.Query

interface AccountDao {
    @Query("SELECT * FROM Account") fun getAll()
    @Delete fun deleteAll()
}