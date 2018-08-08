package com.yury.lebowski.ui.operations

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.enums.OperationState

class OperationTabListAdapter(fm: FragmentManager, tabTitle: Array<String>, accountId: Long) : FragmentStatePagerAdapter(fm) {

    private val tabTitles = tabTitle
    private val accountId = accountId

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return OperationTabFragment.newInstance(OperationState.Normal, accountId)
            1 -> return OperationTabFragment.newInstance(OperationState.Normal, accountId)
            2 -> return OperationTabFragment.newInstance(OperationState.Draft, accountId)
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 3
    }

}