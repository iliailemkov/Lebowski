package com.yury.lebowski.data.repository

import androidx.lifecycle.LiveData
import com.yury.lebowski.data.dao.AccountDao
import com.yury.lebowski.data.models.Account
import javax.inject.Inject

class BalanceRepository @Inject constructor(
        private val accountDao : AccountDao
) {
    fun getBalanceById(id: Long) : LiveData<Account> = accountDao.findById(id)
    fun getBalances() : LiveData<List<Account>> = accountDao.getAll()
}