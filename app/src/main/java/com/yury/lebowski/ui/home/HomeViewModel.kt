package com.yury.lebowski.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.CurrencyType
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val accountRepository : AccountRepository,
        private val operationRepository: OperationRepository
): ViewModel() {


    val accounts by lazy { accountRepository.getBalances() }
    val operations by lazy { operationRepository.getOperations(1) }


    var balanceMain = MutableLiveData<String>()
    var balanceSecondary = MutableLiveData<String>()
    // TODO: use live data transformation (switch map)
    var currency = MutableLiveData<String>()

    //val categories = OperationListAdapter()

    // TODO: use dagger

    /*init {
        val balanceInUniversal = balanceRepository.getAmountInUniversal()
        val rate = rateRepository.getLastOfflineRate(CurrencyType.Ruble)
        balanceMain.value = "$balanceInUniversal $"
        currency.value = rate.toString()
        balanceSecondary.value = "${balanceInUniversal * rate} \u20BD"
    }*/
}
