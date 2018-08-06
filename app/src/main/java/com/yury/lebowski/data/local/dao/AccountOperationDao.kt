package com.yury.lebowski.data.local.dao

import androidx.room.*
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.PeriodicalOperation

@Dao
interface AccountOperationDao {

    @Query("UPDATE Account " +
            "SET balance = balance + :amount " +
            "WHERE id = :accountId ")
    fun updateAmount(amount : Double, accountId: Long)

    @Insert
    fun insertOperation(operation: Operation)

    @Insert
    fun insertPeriodOperation(operation: Operation) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeriod(periodicalOperation: PeriodicalOperation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(operation: Operation)

    @Transaction
    fun  insertOperationAndUpdateAmount(operation: Operation, amount: Double, accountId: Long) {
        insertOperation(operation)
        updateAmount(amount, accountId)
    }

    @Transaction
    fun insertPeriodOperationAndUpdateAmount(operation: Operation,
                                             amount: Double,
                                             accountId: Long,
                                             period: Long) {
        updateAmount(amount, accountId)
        val operationId = insertPeriodOperation(operation)
        insertPeriod(PeriodicalOperation(null, operationId, period))
    }
}