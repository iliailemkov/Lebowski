package com.yury.lebowski

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

interface Navigator {

    fun initToolbar(@StringRes title: Int, elevation: Float)
    fun navigateTo(fragment: Fragment, transaction: String?)
    fun navigateBack()

}