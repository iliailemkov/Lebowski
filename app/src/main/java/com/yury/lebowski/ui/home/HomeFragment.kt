package com.yury.lebowski.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.yury.lebowski.Navigator
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.ui.add_operation.AddOperationFragment
import com.yury.lebowski.ui.home.OperationList.OperationAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    var operationAdapter: OperationAdapter? = null

<<<<<<< HEAD
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        speedDial.inflate(R.menu.menu_speed_dial)
        speedDial.setOnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.manage_categories -> {
                    Toast.makeText(context, getString(R.string.tbd), Toast.LENGTH_SHORT).show()
                    return@setOnActionSelectedListener true
                }
                R.id.add_income -> listener?.onAddOperationClicked(OperationType.Income)
                R.id.add_expenditure -> listener?.onAddOperationClicked(OperationType.Expenditure)
=======
    private val accounts = Observer<List<Account>> { res ->
        if (res !== null) {
            if(!res.isEmpty()) {
                if (res[0].balance < 0) {
                    balance_main.setTextColor(ContextCompat.getColor(context!!, android.R.color.holo_red_dark))
                } else {
                    balance_main.setTextColor(ContextCompat.getColor(context!!, android.R.color.holo_green_dark))
                }
                balance_main.text = String.format("%.2f", res[0].balance)
>>>>>>> develop
            }
        }
    }

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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        initFab()
        initOperationList()
    }

    override fun onStart() {
        super.onStart()
        viewModel.accounts.observe(this, this.accounts)
        viewModel.operations.observe(this, this.operations)
    }

    override fun onStop() {
        super.onStop()
        viewModel.accounts.removeObservers(this)
        viewModel.operations.removeObservers(this)
    }

    private fun initFab() {
        speedDial.inflate(R.menu.menu_speed_dial)
        speedDial.setOnActionSelectedListener { it ->
            when (it.id) {
                R.id.add_income -> (activity as Navigator).navigateTo(AddOperationFragment.newInstance(OperationType.Income), "NaviagteAddIncome")
                R.id.add_expenditure -> (activity as Navigator).navigateTo(AddOperationFragment.newInstance(OperationType.Expenditure), "NaviagteAddExpenditure")
            }
            false
        }
    }

    private fun initOperationList() {
        operationAdapter = OperationAdapter(context!!)
        rv_operation_list.adapter = operationAdapter
        rv_operation_list.layoutManager = LinearLayoutManager(context)
        rv_operation_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

}
