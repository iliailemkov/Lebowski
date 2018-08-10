package com.yury.lebowski.ui.operations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.OperationWrapper
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.ui.add_operation.AddOperationFragment
import com.yury.lebowski.ui.operations.OperationList.OperationAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.operation_recycler.*
import javax.inject.Inject

class OperationTabFragment @Inject constructor(

) : DaggerFragment() {

    val OPERATION_STATE = "operation_state"
    val ACCOUNT_ID = "account_id"

    companion object {
        fun newInstance(operationState: OperationState, accountId: Long) = OperationTabFragment().apply {
            arguments = bundleOf(OPERATION_STATE to operationState.ordinal, ACCOUNT_ID to accountId)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: OperationsViewModel

    var operationAdapter: OperationAdapter? = null

    var operationState: OperationState? = null

    private val operations: Observer<List<OperationWrapper>> = Observer { res ->
        if (res != null) {
            operationAdapter?.operations = res.filter { it.operationState == operationState }
            operationAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operationState = OperationState.findByOrdinal(it.get(OPERATION_STATE) as Int)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.operation_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OperationsViewModel::class.java)
        arguments?.let {
            viewModel.accountId.value = it.get(ACCOUNT_ID) as Long
        }
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val index = item.groupId
        var toast: Toast? = null
        if (getUserVisibleHint()) {
            if (operationState == OperationState.Normal) {
                when (item.order) {
                    0 -> {
                        viewModel.addDraft(Operation(null,
                                operationAdapter!!.operations[index].date,
                                operationAdapter!!.operations[index].operationType,
                                operationAdapter!!.operations[index].operationState,
                                operationAdapter!!.operations[index].amount,
                                operationAdapter!!.operations[index].accountId,
                                operationAdapter!!.operations[index].categoryId))
                        toast = Toast.makeText(context, "addDraft", Toast.LENGTH_SHORT)
                        toast!!.show()
                        return true
                    }
                    1 -> {
                        viewModel.deleteOperation(operationAdapter!!.operations[index].id!!,
                                operationAdapter!!.operations[index].operationState,
                                operationAdapter!!.operations[index].amount,
                                operationAdapter!!.operations[index].accountId)
                        toast = Toast.makeText(context, "Account delete", Toast.LENGTH_SHORT)
                        toast!!.show()
                        return true
                    }
                    else -> return super.onContextItemSelected(item)
                }
            } else {
                when (item.order) {
                    0 -> {
                        viewModel.addOperationFromDraft(Operation(operationAdapter!!.operations[index].id,
                                operationAdapter!!.operations[index].date,
                                operationAdapter!!.operations[index].operationType,
                                operationAdapter!!.operations[index].operationState,
                                operationAdapter!!.operations[index].amount,
                                operationAdapter!!.operations[index].accountId,
                                operationAdapter!!.operations[index].categoryId))
                        toast = Toast.makeText(context, "addOperationFromDraft", Toast.LENGTH_SHORT)
                        toast!!.show()
                        return true
                    }
                    1 -> {
                        viewModel.deleteOperation(operationAdapter!!.operations[index].id!!,
                                operationAdapter!!.operations[index].operationState,
                                operationAdapter!!.operations[index].amount,
                                operationAdapter!!.operations[index].accountId)
                        toast = Toast.makeText(context, "Account delete", Toast.LENGTH_SHORT)
                        toast!!.show()
                        return true
                    }
                    else -> return super.onContextItemSelected(item)
                }
            }
        }
        return false
    }

    private fun initOperationList() {
        operationAdapter = OperationAdapter()
        rv_operation_list.adapter = operationAdapter
        rv_operation_list.layoutManager = LinearLayoutManager(context)
        rv_operation_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}