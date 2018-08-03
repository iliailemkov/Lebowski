package com.yury.lebowski.data.repository

import com.yury.lebowski.data.dao.AccountDao
import com.yury.lebowski.data.dao.OperationDao
import com.yury.lebowski.data.models.Account
import javax.inject.Inject

class BalanceRepository @Inject constructor(
        private val accountDao : AccountDao
) {
    fun getAmountInUniversal() = accountDao.getAll()[0].currentBalanceInUniversal
    fun getAllBalance() : List<Account> = accountDao.getAll()

}