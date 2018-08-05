package com.yury.lebowski.ui.add_operation

import androidx.lifecycle.ViewModel
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.repository.AccountRepository
import javax.inject.Inject

class AddOperationViewModel @Inject constructor(
        private val accountRepository: AccountRepository
        //private val operationType: OperationType
) : ViewModel() {

    val categories = emptyList<String>()//Categories.map { getResourceString(it.nameResourceId) }
    val accounts = emptyList<String>()// Accounts.map { getResourceString(it.nameResourceId) }

    fun addOperation(operation: Operation) {
        accountRepository.addOperation(operation)
    }
    // TODO: find better way to get resources
    private fun getResourceString(resId: Int) = LebowskiApplication.instance.resources.getString(resId)

}
