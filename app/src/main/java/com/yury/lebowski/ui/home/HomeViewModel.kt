package com.yury.lebowski.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var balanceMain = MutableLiveData<String>()
    var balanceSecondary = MutableLiveData<String>()
    var currency = MutableLiveData<String>()


    init {
        balanceMain.value = "5 226 \u20BD"
        balanceSecondary.value = "78 $"
        currency.value = "67"
    }
}
