package com.yury.lebowski.ui.add_operation

import androidx.lifecycle.ViewModel
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class AddOperationViewModel @Inject constructor(
        private val accountRepository: AccountRepository,
        private val operationRepository: OperationRepository
) : ViewModel() {

    val categories = operationRepository.getCategoriesByType(OperationType.Expenditure)
    val accounts = accountRepository.getBalances()

    fun addOperation(operation: Operation) {
        accountRepository.addOperation(operation)
    }

    fun addPeriodicOperation(operation: Operation, id: Long, period: Long) {
        accountRepository.addPeriodicalOperation(operation, id, period)
    }
}
