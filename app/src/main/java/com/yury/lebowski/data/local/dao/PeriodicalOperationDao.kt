package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.PeriodicalOperation

@Dao
interface PeriodicalOperationDao {

    @Query("SELECT * FROM PeriodicalOperation")
    fun getAll() : List<PeriodicalOperation>

    @Query("SELECT * FROM PeriodicalOperation WHERE operationId = :id")
    fun findByoperationId(id: Long) : LiveData<List<PeriodicalOperation>>

    @Insert
    fun insertAll(list: List<PeriodicalOperation>)

}