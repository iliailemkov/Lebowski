package com.yury.lebowski.data.dao

import androidx.room.Delete
import androidx.room.Query

interface CategoryDao {
    @Query("SELECT * FROM Category") fun getAll()
    @Delete fun deleteAll()
}