package com.yury.lebowski.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.ui.home.AccountList.AccountAdapter
import com.yury.lebowski.ui.operations.OperationsFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

@NavigatorMainContainer
class HomeFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    var accountAdapter: AccountAdapter? = null

    private val accounts = Observer<List<Account>> { res ->
        if (res !== null) {
            accountAdapter?.accounts = res
            accountAdapter?.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(R.string.app_name, R.dimen.toolbar_elevation, this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        initAccounts()
    }

    override fun onStart() {
        super.onStart()
        viewModel.accounts.observe(this, this.accounts)
    }

    override fun onStop() {
        super.onStop()
        viewModel.accounts.removeObservers(this)
    }

    private fun initAccounts() {
        accountAdapter = AccountAdapter { id ->
            (activity as Navigator).navigateTo(OperationsFragment.newInstance(id), "NavigateToOperations")
        }
        rv_account_list.adapter = accountAdapter
        rv_account_list.layoutManager = LinearLayoutManager(context)
        rv_account_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    /* private fun initFab() {
         speedDial.inflate(R.menu.menu_speed_dial)
         speedDial.setOnActionSelectedListener { it ->
             when (it.id) {
                 R.id.add_income -> (activity as Navigator).navigateTo(AddOperationFragment.newInstance(OperationType.Income), "NaviagteAddIncome")
                 R.id.add_expenditure -> (activity as Navigator).navigateTo(AddOperationFragment.newInstance(OperationType.Expenditure), "NaviagteAddExpenditure")
             }
             false
         }
     }*/

    /*private fun initOperationList() {
        operationAdapter = OperationAdapter(context!!)
        rv_operation_list.adapter = operationAdapter
        rv_operation_list.layoutManager = LinearLayoutManager(context)
        rv_operation_list.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }*/

}
