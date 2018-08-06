package com.yury.lebowski.ui.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.NavigationDetailContainer
import com.yury.lebowski.ui.operations.OperationList.OperationAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_detail_fragment.*
import javax.inject.Inject

@NavigationDetailContainer
class OperationsFragment : DaggerFragment() {

    val ACCOUNT_ID = "account_id"

    companion object {
        fun newInstance(accountId: Long) = OperationsFragment().apply {
            arguments = bundleOf(ACCOUNT_ID to accountId)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: OperationsViewModel

    var operationAdapter: OperationAdapter? = null

    var accountId : Long? = null

    private val operations: Observer<List<Operation>> = Observer { res ->
        if (res != null) {
            operationAdapter?.operations = res
            operationAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OperationsViewModel::class.java)
        initOperationList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accountId = it.get(ACCOUNT_ID) as Long
        }
        activity?.setTitle(R.string.operations_page_title)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.settings_item)?.isVisible = false
        menu?.findItem(R.id.statistics_item)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        viewModel.operations.observe(this, this.operations)
    }

    override fun onStop() {
        super.onStop()
        viewModel.operations.removeObservers(this)
    }

    private fun initOperationList() {
        operationAdapter = OperationAdapter(context!!)
        rv_operation_list.adapter = operationAdapter
        rv_operation_list.layoutManager = LinearLayoutManager(context)
        rv_operation_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }
}