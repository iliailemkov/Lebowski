package com.yury.lebowski.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.yury.lebowski.data.local.models.Operation

@Dao
interface AccountOperationDao {

    @Query("UPDATE Account " +
            "SET currentBalanceInUniversal = currentBalanceInUniversal + :amount " +
            "WHERE id = :accountId ")
    fun updateAmount(amount : Double, accountId: Long)

    @Insert fun insertOperation(operation: Operation)

    @Transaction
    fun insertOperationAndUpdateAmount(operation: Operation, amount: Double, accountId: Long) {
        insertOperation(operation)
        updateAmount(amount, accountId)
    }
}