package com.yury.lebowski.data.repository

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.AccountOperationDao
import com.yury.lebowski.data.local.dao.ExchangeRateDao
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.ExchangeRate
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.remote.api.ExchangeApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
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

    fun getRate(currencyFrom: CurrencyType, currencyTo: CurrencyType): Double {
        Executors.newSingleThreadScheduledExecutor().execute {
            exchangeRateDao.findByCurrency(currencyFrom, currencyTo)
            ExchangeApi.create().getExhangeRate(currencyFrom.code + "_" + currencyTo.code, "y").enqueue(object : Callback<ResponseBody> {

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.body() != null) {
                        exchangeRateDao.insertAll(listOf(
                                ExchangeRate(null, currencyFrom, currencyTo, response.body()?.string().toString().substringAfterLast(":").dropLast(2).toDouble(), Date())))
                    }
                }
            })
        }
        return exchangeRateDao.getAll().findLast { DateUtils.isToday(it.date.time) }?.rate!!
    }

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