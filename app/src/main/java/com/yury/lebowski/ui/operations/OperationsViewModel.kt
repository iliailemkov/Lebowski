package com.yury.lebowski.ui.operations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class OperationsViewModel @Inject constructor(
        private val operationRepository: OperationRepository
) : ViewModel() {

    var accountId = MutableLiveData<Long>()
        set(value) {
            accountId.value = value.value
        }

    val operations = Transformations.switchMap(accountId) { operationRepository.getOperations(accountId.value?:1) }

}