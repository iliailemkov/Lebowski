package com.yury.lebowski.ui.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.ui.operations.OperationList.OperationAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.operation_recycler.*
import javax.inject.Inject

class OperationTabFragment @Inject constructor(

) : DaggerFragment() {

    companion object {
        fun newInstance() = OperationTabFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: OperationsViewModel

    var operationAdapter: OperationAdapter? = null

    var accountId: Long? = null

    private val operations: Observer<List<Operation>> = Observer { res ->
        if (res != null) {
            operationAdapter?.operations = res
            operationAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.operation_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OperationsViewModel::class.java)
        initOperationList()
    }

    override fun onStart() {
        super.onStart()
        viewModel.operations.observe(this, operations)
    }

    override fun onStop() {
        super.onStop()
        viewModel.operations.removeObservers(this)
    }

    private fun initOperationList() {
        operationAdapter = OperationAdapter()
        rv_operation_list.adapter = operationAdapter
        rv_operation_list.layoutManager = LinearLayoutManager(context)
        rv_operation_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}