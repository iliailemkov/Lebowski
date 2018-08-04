package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.dao.CategoryDao
import com.yury.lebowski.data.dao.OperationDao
import com.yury.lebowski.data.models.Category
import com.yury.lebowski.data.models.Operation
import javax.inject.Inject

class OperationRepository @Inject constructor(
        private val operationDao: OperationDao,
        private val categoryDao: CategoryDao
) {
    fun getAllOperations() : LiveData<List<Operation>> = operationDao.getAll()
    fun getOperations(accountId : Long) : LiveData<List<Operation>> = operationDao.findByAccountId(accountId)
    fun getAllCategories() : LiveData<List<Category>> = categoryDao.getAll()
}