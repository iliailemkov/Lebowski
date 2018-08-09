package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.CategoryDao
import com.yury.lebowski.data.local.dao.OperationDao
import com.yury.lebowski.data.local.dao.PeriodicalOperationDao
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.PeriodicalOperation
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class OperationRepository @Inject constructor(
        private val accountDao: AccountDao,
        private val operationDao: OperationDao,
        private val categoryDao: CategoryDao,
        private val periodicalOperationDao: PeriodicalOperationDao
) {
    fun getAllOperations(): LiveData<List<Operation>> = operationDao.getAll()

    fun getOperations(accountId: Long): LiveData<List<Operation>> = operationDao.findByAccountId(accountId)

    fun getFilterOperations(startDate: Date, finishDate: Date, accountId: Long): LiveData<List<Operation>> =
            operationDao.filterByPeriodAndAccountId(startDate, finishDate, accountId)

    fun deleteOperation(operationId: Long) {
        operationDao.delete(operationId)
    }

    fun getAllCategories(): LiveData<List<Category>> = categoryDao.getAll()

    fun getOperationById(id: Long): Operation = operationDao.findById(id)

    fun getCategoriesByType(operationType: OperationType): LiveData<List<Category>> = categoryDao.filterByType(operationType)

    fun getAllPeriodicalOperations(): List<PeriodicalOperation> = periodicalOperationDao.getAll()

    fun addDraftOperation(operation: Operation) {
        Executors.newSingleThreadScheduledExecutor().submit() {
            operationDao.insertOperation(Operation(null, operation.date, operation.operationType, OperationState.Draft, operation.amount, operation.accountId, operation.categoryId))
        }
    }
}