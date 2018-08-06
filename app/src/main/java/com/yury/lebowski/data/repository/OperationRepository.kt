package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.local.dao.*
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.data.local.models.PeriodicalOperation
import javax.inject.Inject

class OperationRepository @Inject constructor(
        private val accountDao : AccountDao,
        private val operationDao: OperationDao,
        private val categoryDao: CategoryDao,
        private val periodicalOperationDao: PeriodicalOperationDao
) {
    fun getAllOperations() : LiveData<List<Operation>> = operationDao.getAll()

    fun getOperations(accountId : Long) : LiveData<List<Operation>> = operationDao.findByAccountId(accountId)

    fun getAllCategories() : LiveData<List<Category>> = categoryDao.getAll()

    fun getOperationById(id : Long) : Operation = operationDao.findById(id)

    fun getCategoriesByType(operationType : OperationType) : LiveData<List<Category>> = categoryDao.filterByType(operationType)

    fun getAllPeriodicalOperations() : List<PeriodicalOperation> = periodicalOperationDao.getAll()
}