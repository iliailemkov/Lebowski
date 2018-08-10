package com.yury.lebowski.ui.add_account

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_account_fragment.*
import javax.inject.Inject
import android.text.Editable
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.EditText
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.enums.CurrencyType


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
        (activity as Navigator).initToolbar(R.string.add_account, R.dimen.toolbar_elevation, this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddAccountViewModel::class.java)
        spinner_currency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, CurrencyType.values().map { c -> c.code })
        initInputName()
        initAddButton()
    }

    private fun initInputName() {
        et_account_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                add_account_button.isEnabled = validateAccountName(s)
            }
        })
        et_account_name.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                add_account_button.isEnabled = validateAccountName((view as EditText).text)

            }
        }
    }

    private fun initAddButton() {
        add_account_button.isEnabled = false
        add_account_button.setOnClickListener {
            viewModel.addAccount(Account(null, et_account_name.text.toString(), 0.0, CurrencyType.findByCode(spinner_currency.adapter.getItem(spinner_currency.selectedItemPosition).toString())!!))
            (activity as Navigator).navigateBack()
        }
    }

    private fun validateAccountName(editable: Editable) : Boolean {
        if (!TextUtils.isEmpty(editable)) {
            til_account_name.setError(null)
        }
        else {
            til_account_name.setError(getString(R.string.account_incorrect_name))
        }
        return !TextUtils.isEmpty(editable)
    }
}