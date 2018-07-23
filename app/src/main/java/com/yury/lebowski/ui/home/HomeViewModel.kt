package com.yury.lebowski.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yury.lebowski.models.CurrencyType
import com.yury.lebowski.repository.BalanceRepository
import com.yury.lebowski.repository.convertFromUniversal

class HomeViewModel : ViewModel() {
    var balanceMain = MutableLiveData<String>()
    var balanceSecondary = MutableLiveData<String>()
    var currency = MutableLiveData<String>()

    private val balanceRepository = BalanceRepository()

    init {
        val balanceInUniversal = balanceRepository.getAmountInUniversal()
        val rate =  CurrencyType.Ruble.convertFromUniversal()
        balanceMain.value =  "$balanceInUniversal $"
        currency.value = "Rate: ${rate.toString()}"
        balanceSecondary.value =  "${balanceInUniversal * rate} \u20BD"

    }
}
