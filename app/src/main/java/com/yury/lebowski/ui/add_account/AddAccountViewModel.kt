package com.yury.lebowski.ui.add_account

import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.repository.AccountRepository
import javax.inject.Inject

class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    fun addAccount(account: Account) {
        accountRepository.addAccount(account)
    }
}