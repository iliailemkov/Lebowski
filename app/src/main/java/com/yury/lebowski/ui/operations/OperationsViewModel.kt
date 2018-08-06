package com.yury.lebowski.ui.operations

import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.repository.OperationRepository
import javax.inject.Inject

class OperationsViewModel @Inject constructor(
        private val operationRepository: OperationRepository
) : ViewModel() {

    val operations by lazy { operationRepository.getOperations(1) }

}