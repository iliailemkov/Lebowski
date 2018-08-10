package com.yury.lebowski.ui.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.NavigatiorDetailContainer
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.ui.operations.OperationList.OperationAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.operation_fragment.*
import kotlinx.android.synthetic.main.operation_recycler.*
import javax.inject.Inject


@NavigatiorDetailContainer
class OperationsFragment : DaggerFragment() {

    val ACCOUNT_ID = "account_id"

    companion object {
        fun newInstance(accountId: Long) = OperationsFragment().apply {
            arguments = bundleOf(ACCOUNT_ID to accountId)
        }
    }

    var operationAdapter: OperationAdapter? = null

    var accountId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.operation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(R.string.operations_page_title, null, this)
        val tabTitles = arrayOf(getString(R.string.operation_page_tab_history),
                getString(R.string.operation_page_tab_periodical),
                getString(R.string.operation_page_tab_draft))
        val adapter = OperationTabListAdapter(childFragmentManager!!, tabTitles, accountId!!)
        stockViewPager.adapter = adapter
        stockTabLayout.setupWithViewPager(stockViewPager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountId = it.get(ACCOUNT_ID) as Long
        }
    }
}