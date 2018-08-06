package com.yury.lebowski.ui.add_operation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class AddOperationViewModel @Inject constructor(
        private val accountRepository: AccountRepository,
        private val operationRepository: OperationRepository
) : ViewModel() {

    var filterCategory = MutableLiveData<OperationType>()
        set(value) {
            filterCategory.value = value.value
        }

    val categories = Transformations.switchMap(filterCategory) { operationRepository.getCategoriesByType(filterCategory.value ?: OperationType.Expenditure) }
    val accounts = accountRepository.getBalances()

    fun addOperation(operation: Operation, currencyType: CurrencyType) {
        accountRepository.addOperation(operation, currencyType)
    }

    fun addPeriodicOperation(operation: Operation, id: Long, period: Long, currencyType: CurrencyType) {
        accountRepository.addPeriodicalOperation(operation, id, period, currencyType)
    }
}
