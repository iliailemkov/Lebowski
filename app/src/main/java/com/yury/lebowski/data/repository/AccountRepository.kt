package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.AccountOperationDao
import com.yury.lebowski.data.local.dao.ExchangeRateDao
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationState
import java.util.concurrent.Executors
import javax.inject.Inject

class AccountRepository @Inject constructor(
        private val accountDao: AccountDao,
        private val accountOperationDao: AccountOperationDao,
        private val exchangeRateDao: ExchangeRateDao
) {
    fun getBalanceById(id: Long): Account = accountDao.findById(id)

    fun getBalances(): LiveData<List<Account>> = accountDao.getAll()

    fun getRates() = exchangeRateDao.getAll()

    fun deleteAccount(accountId: Long) {
        Executors.newSingleThreadScheduledExecutor().execute {
            accountDao.delete(accountId)
        }
    }

    fun deleteOperation(operationId: Long, amount: Double, accountId: Long) {
        Executors.newSingleThreadScheduledExecutor().execute {
            accountOperationDao.deleteOperationAndUpdateAmount(operationId, amount, accountId)
        }
    }

    fun updateRates(currencyFrom: CurrencyType, currencyTo: CurrencyType) {}

    fun getRate(currencyFrom: CurrencyType, currencyTo: CurrencyType): Double = exchangeRateDao.findByCurrency(currencyFrom, currencyTo)

    fun addAccount(account: Account) {
        Executors.newSingleThreadScheduledExecutor().execute {
            accountDao.insert(account)
        }
    }

    fun addOperation(operation: Operation, currencyType: CurrencyType) {
        Executors.newSingleThreadScheduledExecutor().execute {
            val account = getBalanceById(operation.accountId)
            if (!currencyType.code.equals(account.currencyType.code)) {
                val rate = getRate(currencyType, account.currencyType)
                val newAmount = operation.amount * rate
                val newOperation = Operation(null,
                        operation.date,
                        operation.operationType,
                        OperationState.Normal,
                        newAmount,
                        operation.accountId,
                        operation.categoryId)
                accountOperationDao.insertOperationAndUpdateAmount(newOperation, newAmount, operation.accountId)
            } else {
                accountOperationDao.insertOperationAndUpdateAmount(operation, operation.amount, operation.accountId)
            }
        }
    }

    fun addOperationFromDraft(operation: Operation) {
        Executors.newSingleThreadScheduledExecutor().execute {
            accountOperationDao.insertOperationAndUpdateAmount(operation, operation.amount, operation.accountId)
        }
    }

    fun addPeriodicalOperation(operation: Operation, id: Long, period: Long, currencyType: CurrencyType) {
        Executors.newSingleThreadScheduledExecutor().execute {
            val account = getBalanceById(operation.accountId)
            if (!currencyType.code.equals(account.currencyType.code)) {
                val rate = getRate(currencyType, account.currencyType)
                val newAmount = operation.amount * rate
                val newOperation = Operation(null,
                        operation.date,
                        operation.operationType,
                        OperationState.Normal,
                        newAmount,
                        operation.accountId,
                        operation.categoryId)
                accountOperationDao.insertOperationAndUpdateAmount(newOperation, newAmount, operation.accountId)
            } else {
                accountOperationDao.insertPeriodOperationAndUpdateAmount(operation, operation.amount, operation.accountId, period)
                accountOperationDao.insertOperation(Operation(null,
                        operation.date,
                        operation.operationType,
                        OperationState.Periodical,
                        operation.amount,
                        operation.accountId,
                        operation.categoryId))
            }
        }
    }
}