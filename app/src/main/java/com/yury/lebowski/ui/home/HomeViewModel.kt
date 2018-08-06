package com.yury.lebowski.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val accountRepository : AccountRepository,
        private val operationRepository: OperationRepository
): ViewModel() {
    val accounts by lazy { accountRepository.getBalances() }
}
