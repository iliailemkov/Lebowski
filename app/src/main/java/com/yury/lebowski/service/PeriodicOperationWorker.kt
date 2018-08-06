package com.yury.lebowski.service

import androidx.work.Worker
import com.yury.lebowski.data.local.db.LebowskiDb
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.PeriodicalOperation
import java.util.*

class PeriodicOperationWorker: Worker() {

    companion object {
        internal val TAG = "PeriodicOperationWorker"
    }

    override fun doWork(): Result {

        LebowskiDb.getInstance(applicationContext).periodicalOperationDao().getAll().forEach { it ->
            if(it != null) {
                check(it)
            }
        }
        return Result.SUCCESS
    }

    private fun check(periodicalOperations : PeriodicalOperation) {
        val operation = LebowskiDb.getInstance(applicationContext).operationDao().findById(periodicalOperations.operationId)
        val currentDate = System.currentTimeMillis()
        while (periodicalOperations.period + operation.date.time < currentDate) {
            addOperation(Operation( null,
                    Date(periodicalOperations.period + operation.date.time),
                    operation.operationType ,
                    operation.amount,
                    operation.accountId,
                    operation.categoryId), periodicalOperations.operationId, periodicalOperations.period )
        }
    }

    private fun addOperation(operation: Operation, id: Long, period: Long) {
        LebowskiDb.getInstance(applicationContext).accountOperationDao().insertPeriodOperationAndUpdateAmount(operation, operation.amount, operation.accountId, period)
    }
}