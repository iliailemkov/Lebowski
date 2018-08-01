package com.yury.lebowski.ui.add_operation

import androidx.lifecycle.ViewModel
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.models.Accounts
import com.yury.lebowski.models.Categories
import com.yury.lebowski.models.OperationType

class AddOperationViewModel(private val operationType: OperationType) : ViewModel() {
    val categories = Categories.filter { it.operationType == operationType }.map { getResourceString(it.nameResourceId) }
    val accounts = Accounts.map { getResourceString(it.nameResourceId) }

    // TODO: find better way to get resources
    private fun getResourceString(resId: Int) = LebowskiApplication.instance.resources.getString(resId)

}
