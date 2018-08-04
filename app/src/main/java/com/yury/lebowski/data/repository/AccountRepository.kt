package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.LebowskiDb
import com.yury.lebowski.data.PrepopulateData
import com.yury.lebowski.data.dao.AccountDao
import com.yury.lebowski.data.dao.AccountOperationDao
import com.yury.lebowski.data.models.Account
import com.yury.lebowski.data.models.Operation
import java.util.concurrent.Executors
import javax.inject.Inject

class AccountRepository @Inject constructor(
        private val accountDao : AccountDao,
        private val accountOperationDao : AccountOperationDao
) {
    fun getBalanceById(id: Long) : LiveData<Account> = accountDao.findById(id)
    fun getBalances() : LiveData<List<Account>> = accountDao.getAll()
    fun addOperation(operation: Operation) {
        Executors.newSingleThreadScheduledExecutor().execute(Runnable {
            accountOperationDao.insertOperationAndUpdateAmount(operation, operation.amountInUniversal, operation.accountId)
        })
    }
}