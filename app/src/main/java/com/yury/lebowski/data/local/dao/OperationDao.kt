package com.yury.lebowski.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomWarnings
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.OperationWrapper
import java.util.*

@Dao
interface OperationDao {

    @Insert
    fun insertOperation(operation: Operation)

    @Query("SELECT * FROM Operation")
    fun getAll(): LiveData<List<Operation>>

    @Query("SELECT * FROM Operation WHERE id = :id")
    fun findById(id: Long): Operation

    @Query("SELECT * FROM Operation WHERE accountId = :id")
    fun findByAccountId(id: Long): LiveData<List<Operation>>

    @Query("SELECT * FROM Operation WHERE accountId = :id AND date > :startDate AND date < :finishDate")
    fun filterByPeriodAndAccountId(startDate: Date?, finishDate: Date?, id: Long): LiveData<List<Operation>>

    @Query("SELECT Operation.id as id, Operation.date, Operation.operationType as operationType, Operation.operationState as operationState, Operation.amount, Account.id as accountId, Category.id as categoryId, Category.name as categoryName FROM Operation INNER JOIN Account ON Operation.accountId = Account.id INNER JOIN Category ON Operation.categoryId = Category.id WHERE Operation.accountId = :account")
    fun getOperationWrapper(account: Long): LiveData<List<OperationWrapper>>

    @Query("SELECT Operation.id as id, Operation.date, Operation.operationType as operationType, Operation.operationState as operationState, Operation.amount, Account.id as accountId, Category.id as categoryId, Category.name as categoryName FROM Operation INNER JOIN Account ON Operation.accountId = Account.id INNER JOIN Category ON Operation.categoryId = Category.id WHERE Operation.accountId = :account")
    fun getOperationWrapperByPeriod(account: Long): LiveData<List<OperationWrapper>>

    @Query("DELETE FROM Operation WHERE :id = id")
    fun delete(id: Long)

    @Insert
    fun insertAll(list: List<Operation>)
}