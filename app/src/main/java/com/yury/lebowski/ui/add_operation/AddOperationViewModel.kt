package com.yury.lebowski.ui.add_operation

import androidx.lifecycle.ViewModel
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.data.repository.BalanceRepository
import javax.inject.Inject

class AddOperationViewModel @Inject constructor(
        private val balanceRepository: BalanceRepository
        //private val operationType: OperationType
) : ViewModel() {
    val categories = emptyList<String>()//Categories.map { getResourceString(it.nameResourceId) }
    val accounts = emptyList<String>()// Accounts.map { getResourceString(it.nameResourceId) }

    // TODO: find better way to get resources
    private fun getResourceString(resId: Int) = LebowskiApplication.instance.resources.getString(resId)

}
