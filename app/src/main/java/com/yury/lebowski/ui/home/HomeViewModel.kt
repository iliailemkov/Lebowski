package com.yury.lebowski.ui.home

<<<<<<< HEAD
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yury.lebowski.models.CurrencyType
import com.yury.lebowski.repository.BalanceRepository
import com.yury.lebowski.repository.RateRepository

class HomeViewModel : ViewModel() {
    var balanceMain = MutableLiveData<String>()
    var balanceSecondary = MutableLiveData<String>()
    // TODO: use live data transformation (switch map)
    var currency = MutableLiveData<String>()

    // TODO: use dagger
    private val balanceRepository = BalanceRepository()
    private val rateRepository = RateRepository()

    init {
        val balanceInUniversal = balanceRepository.getAmountInUniversal()
        val rate =  rateRepository.getLastOfflineRate(CurrencyType.Ruble)
        balanceMain.value =  "$balanceInUniversal $"
        currency.value = rate.toString()
        balanceSecondary.value =  "${balanceInUniversal * rate} \u20BD"
    }
=======
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val accountRepository : AccountRepository,
        private val operationRepository: OperationRepository
): ViewModel() {
    val accounts by lazy { accountRepository.getBalances() }
    val operations by lazy { operationRepository.getOperations(1) }
>>>>>>> develop
}
