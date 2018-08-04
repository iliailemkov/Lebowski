package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.AccountOperationDao
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.remote.api.ExchangeApi
import java.util.concurrent.Executors
import javax.inject.Inject

class AccountRepository @Inject constructor(
        private val accountDao : AccountDao,
        private val accountOperationDao : AccountOperationDao
) {
    fun getBalanceById(id: Long) : LiveData<Account> = accountDao.findById(id)

    fun getBalances() : LiveData<List<Account>> = accountDao.getAll()

    fun addOperation(operation: Operation) {
        Executors.newSingleThreadScheduledExecutor().execute {
            ExchangeApi.create().getExhangeRate(operation.currencyType.code + "_" + getBalanceById(operation.accountId).value?.currencyType?.code, "y")
            accountOperationDao.insertOperationAndUpdateAmount(operation, operation.amountInUniversal, operation.accountId)
        }
    }
}