package com.yury.lebowski.util.data_binding

import androidx.databinding.DataBindingComponent
import com.yury.lebowski.util.data_binding.spinner.InverseSpinnerBindings
import com.yury.lebowski.util.data_binding.spinner.SpinnerBindings

class BindingComponent: DataBindingComponent {
    override fun getSpinnerBindings(): SpinnerBindings {
        return SpinnerBindings()
    }


    override fun getInverseSpinnerBindings(): InverseSpinnerBindings {
        return InverseSpinnerBindings()
    }
}