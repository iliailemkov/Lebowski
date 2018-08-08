package com.yury.lebowski.ui.operations

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yury.lebowski.R

class OperationTabListAdapter(fm: FragmentManager, tabTitle: Array<String>) : FragmentStatePagerAdapter(fm) {

    private val tabTitles = tabTitle

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return OperationTabFragment.newInstance()
            1 -> return OperationTabFragment.newInstance()
            2 -> return OperationTabFragment.newInstance()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 3
    }

}