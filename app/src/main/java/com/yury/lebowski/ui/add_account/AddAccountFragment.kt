package com.yury.lebowski.ui.add_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_account_fragment.*
import javax.inject.Inject

@NavigatorMainContainer
class AddAccountFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var operationType: OperationType? = null
    private lateinit var viewModel: AddAccountViewModel

    companion object {
        fun newInstance() = AddAccountFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.yury.lebowski.R.layout.add_account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(R.string.add_account_page_title, R.dimen.toolbar_elevation, this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddAccountViewModel::class.java)
        //spinner_currency.adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, CurrencyType.values().map { c -> c.code })
        //viewModel.filterCategory.value = operationType
        initAddButton()
    }


    private fun initAddButton() {
        add_account_button.setOnClickListener {
            try {
                //addOperation()
            } catch (e: Exception) {
                Toast.makeText(context, com.yury.lebowski.R.string.incorrect_data, Toast.LENGTH_SHORT).show()
            }
        }
    }
}