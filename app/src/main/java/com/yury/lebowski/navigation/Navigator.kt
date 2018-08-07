package com.yury.lebowski.navigation

import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment


interface Navigator {
    fun initToolbar(@StringRes title: Int, @DimenRes elevation: Int, fragment: Fragment)
    fun navigateTo(fragment: Fragment, transaction: String?)
    fun navigateBack()
    fun isWindowWithDetail(): Boolean
}