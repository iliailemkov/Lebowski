package com.yury.lebowski.navigation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


interface Navigator {
    fun initToolbar(@StringRes title: Int, elevation: Float)
    fun navigateTo(fragment: Fragment, transaction: String?)
    fun navigateBack()
    fun isWindowWithDetail() : Boolean
}