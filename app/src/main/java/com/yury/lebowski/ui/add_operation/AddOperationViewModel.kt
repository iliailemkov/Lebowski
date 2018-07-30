package com.yury.lebowski.ui.add_operation

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.models.Categories
import com.yury.lebowski.models.OperationType

class AddOperationViewModel(private val operationType: OperationType) : ViewModel() {

    // TODO: better way to get resources
    var categories = ObservableField(Categories.filter { it.operationType == operationType }.map { LebowskiApplication.instance.resources.getString(it.nameResourceId) })

}
