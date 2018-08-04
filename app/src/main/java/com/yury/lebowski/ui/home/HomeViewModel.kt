package com.yury.lebowski.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.models.Account
import com.yury.lebowski.data.models.CurrencyType
import com.yury.lebowski.data.repository.BalanceRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.RateRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val balanceRepository : BalanceRepository,
        private val operationRepository: OperationRepository
): ViewModel() {


    val accounts by lazy { balanceRepository.getBalances() }
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
