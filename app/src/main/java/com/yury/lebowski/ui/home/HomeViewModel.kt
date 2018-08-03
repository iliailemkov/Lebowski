package com.yury.lebowski.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.models.CurrencyType
import com.yury.lebowski.data.repository.BalanceRepository
import com.yury.lebowski.data.repository.RateRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val balanceRepository : BalanceRepository,
        private val rateRepository : RateRepository
): ViewModel() {

    var balanceMain = MutableLiveData<String>()
    var balanceSecondary = MutableLiveData<String>()
    // TODO: use live data transformation (switch map)
    var currency = MutableLiveData<String>()

    // TODO: use dagger

    init {
        val balanceInUniversal = balanceRepository.getAmountInUniversal()
        val rate = rateRepository.getLastOfflineRate(CurrencyType.Ruble)
        balanceMain.value = "$balanceInUniversal $"
        currency.value = rate.toString()
        balanceSecondary.value = "${balanceInUniversal * rate} \u20BD"
    }
}
