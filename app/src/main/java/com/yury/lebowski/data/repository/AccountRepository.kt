package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.AccountOperationDao
import com.yury.lebowski.data.local.dao.ExchangeRateDao
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.CurrencyType
import java.util.concurrent.Executors
import javax.inject.Inject

class AccountRepository @Inject constructor(
        private val accountDao : AccountDao,
        private val accountOperationDao : AccountOperationDao,
        private val exchangeRateDao: ExchangeRateDao
) {
    fun getBalanceById(id: Long) : Account = accountDao.findById(id)

    fun getBalances() : LiveData<List<Account>> = accountDao.getAll()

    fun getRates() = exchangeRateDao.getAll()

    fun updateRates(currencyFrom: CurrencyType, currencyTo: CurrencyType) {}

    fun getRate(currencyFrom: CurrencyType, currencyTo: CurrencyType) : Double  = exchangeRateDao.findByCurrency(currencyFrom, currencyTo)

    fun addOperation(operation: Operation, currencyType: CurrencyType) {
        Executors.newSingleThreadScheduledExecutor().execute {
            val account = getBalanceById(operation.accountId)
            if(!currencyType.code.equals(account.currencyType.code)) {
                val rate = getRate(currencyType, account.currencyType)
                val newAmount = operation.amount * rate
                val newOperation = Operation(null,
                        operation.date,
                        operation.operationType,
                        newAmount,
                        operation.accountId,
                        operation.categoryId)
                accountOperationDao.insertOperationAndUpdateAmount(newOperation, newAmount, operation.accountId)
            } else {
                accountOperationDao.insertOperationAndUpdateAmount(operation, operation.amount, operation.accountId)
            }
        }
    }

    fun addPeriodicalOperation(operation: Operation, id: Long, period: Long, currencyType: CurrencyType) {
        Executors.newSingleThreadScheduledExecutor().submit() {
            val account = getBalanceById(operation.accountId)
            if(!currencyType.code.equals(account.currencyType.code)) {
                val rate = getRate(currencyType, account.currencyType)
                val newAmount = operation.amount * rate
                val newOperation = Operation(null,
                        operation.date,
                        operation.operationType,
                        newAmount,
                        operation.accountId,
                        operation.categoryId)
                accountOperationDao.insertOperationAndUpdateAmount(newOperation, newAmount, operation.accountId)
            } else {
                accountOperationDao.insertPeriodOperationAndUpdateAmount(operation, operation.amount, operation.accountId, period)
            }
        }
    }
}