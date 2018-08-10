package com.yury.lebowski.ui.get_summary

import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.repository.AccountRepository
import javax.inject.Inject

class GetSummaryViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    val accounts by lazy { accountRepository.getBalances() }
}