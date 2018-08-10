package com.yury.lebowski.ui.operations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import java.util.*
import javax.inject.Inject


class OperationsViewModel @Inject constructor(
        private val operationRepository: OperationRepository,
        private val accountRepository: AccountRepository
) : ViewModel() {

    var tab = 0

    var accountId = MutableLiveData<Long>()
        set(value) {
            accountId.value = value.value
        }

    var operationState : OperationState = OperationState.Normal

    val operations = Transformations.switchMap(accountId) { operationRepository.getWrapperOperations(accountId.value?:1) }

    fun deleteOperation(operationId: Long, operationState: OperationState, amount: Double, accountId: Long) {
        accountRepository.deleteOperation(operationId, if(operationState == OperationState.Draft) (-1 * amount) else 0.0, accountId)
    }

    fun addDraft(operation: Operation) = operationRepository.addDraftOperation(operation)

    fun addOperationFromDraft(operation: Operation) {
        accountRepository.addOperationFromDraft(Operation(null, Date(), operation.operationType, OperationState.Normal, operation.amount, operation.accountId, operation.categoryId))
    }
}