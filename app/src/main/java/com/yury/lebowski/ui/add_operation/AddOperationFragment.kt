package com.yury.lebowski.ui.add_operation

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.ui.CustomSpinnerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_operation_fragment.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


@NavigatorMainContainer
class AddOperationFragment : DaggerFragment(), View.OnFocusChangeListener {

    val OPERATION_TYPE = "operation_type"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var alertDialog : AlertDialog? = null

    private var operationType: OperationType? = null
    private lateinit var viewModel: AddOperationViewModel

    companion object {
        fun newInstance(operationType: OperationType) = AddOperationFragment().apply {
            arguments = bundleOf(OPERATION_TYPE to operationType)
        }
    }

    private val categoryList: Observer<List<Category>> = Observer { res ->
        if (res != null) {
            categories.adapter = CustomSpinnerAdapter(context!!, res)
        }
    }

    private val accountList: Observer<List<Account>> = Observer { res ->
        if (res != null) {
            accounts.adapter = CustomSpinnerAdapter(context!!, res)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operationType = it.get(OPERATION_TYPE) as OperationType
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_operation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(
                if (operationType == OperationType.Expenditure) R.string.add_expenditure else R.string.add_income,
                R.dimen.toolbar_elevation,
                this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddOperationViewModel::class.java)
        spinner_currency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, CurrencyType.values().map { c -> c.code })
        viewModel.filterCategory.value = operationType
        initAmount()
        initAddButton()
        initPeriodic()
        initDayNumberPicker()
    }

    override fun onStart() {
        super.onStart()
        viewModel.accounts.observe(this, accountList)
        viewModel.categories.observe(this, categoryList)
    }

    override fun onStop() {
        super.onStop()
        viewModel.accounts.removeObservers(this)
        viewModel.categories.removeObservers(this)
    }

    private fun initAddButton() {
        val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        if (switch_periodic.isChecked) {
            add_button.startAnimation(slideDown);
        }
        add_button.setOnClickListener {
            try {
                addOperation()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.incorrect_data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFocusChange(view: View?, condition: Boolean) {
    }

    private fun initAmount() {
        moneyEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }
            private var current = ""
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(!s.toString().equals(current)){
                    moneyEditText.removeTextChangedListener(this);
                    var cleanString: String = s.toString().replace("[,.\\s+]".toRegex(), "")
                    var parsed = cleanString.toDouble()
                    var formatted = NumberFormat.getInstance().format((parsed/100));
                    current = formatted;
                    moneyEditText.setText(formatted);
                    moneyEditText.setSelection(formatted.length);
                    moneyEditText.addTextChangedListener(this);
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun initPeriodic() {
        switch_periodic.isChecked = false
        operation_preiodic_layout.visibility = View.GONE
        switch_periodic.setOnCheckedChangeListener { buttonView, isChecked ->
            val slideInLeft = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            val slideOutRight = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
            if (isChecked) {
                operation_preiodic_layout.startAnimation(slideInLeft);
                operation_preiodic_layout.visibility = View.VISIBLE
            } else {
                operation_preiodic_layout.startAnimation(slideOutRight);
                operation_preiodic_layout.visibility = View.GONE
            }
        }
    }

    private fun initDayNumberPicker() {
        val dialog = AlertDialog.Builder(context!!)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.periodical_picker, null)
        dialog.setTitle(R.string.number_picker_title).setMessage(R.string.number_picker_message).setView(dialogView)
        val numberPicker = dialogView.findViewById(R.id.dialog_number_picker) as NumberPicker
        numberPicker.maxValue = 365
        numberPicker.minValue = 1
        numberPicker.wrapSelectorWheel = false
        dialog.setPositiveButton(R.string.number_picker_done) {
            dialogInterface, i -> operation_preiodic_input.setText(numberPicker.value.toString())
        }
        dialog.setNegativeButton(R.string.number_picker_cancel) { dialogInterface, i -> }
        alertDialog = dialog.create()
        operation_preiodic_input.setOnClickListener {
            alertDialog!!.show()
        }
    }

    private fun validateAmount() : Boolean {
        if (!TextUtils.isEmpty(moneyEditText.text)) {
            money_layout.setError(null)
        }
        else {
            money_layout.setError(getString(R.string.operation_incorrect_value))
        }
        return !TextUtils.isEmpty(moneyEditText.text)
    }

    private fun validatePeriodic() : Boolean {
        if (!TextUtils.isEmpty(operation_preiodic_input.text)) {
            operation_preiodic_layout.setError(null)
        }
        else {
            operation_preiodic_layout.setError(getString(R.string.operation_incorrect_value))
        }
        return !TextUtils.isEmpty(operation_preiodic_input.text)
    }

    private fun addOperation() {
        if(validateAmount()) {
            if (switch_periodic.isChecked) {
                if(validatePeriodic()) {
                    viewModel.addPeriodicOperation(Operation(null,
                            Date(),
                            operationType!!,
                            OperationState.Normal,
                            moneyEditText.text.toString().replace("[,.\\s+]".toRegex(), "").toDouble() / 100 * operationType?.effect!!,
                            (accounts.adapter.getItem(accounts.selectedItemPosition) as Account).id!!,
                            (categories.adapter.getItem(categories.selectedItemPosition) as Category).id!!), 1,
                                                                                        operation_preiodic_input.text.toString().toLong(),
                            CurrencyType.findByCode(spinner_currency.adapter.getItem(spinner_currency.selectedItemPosition).toString())!!)
                    (activity as Navigator).navigateBack()
                }
            } else {
                viewModel.addOperation(
                        Operation(null,
                                Date(),
                                operationType!!,
                                OperationState.Normal,
                                moneyEditText.text.toString().replace("[,.\\s+]".toRegex(), "").toDouble() / 100 * operationType?.effect!!,
                                (accounts.adapter.getItem(accounts.selectedItemPosition) as Account).id!!,
                                (categories.adapter.getItem(categories.selectedItemPosition) as Category).id!!),
                        CurrencyType.findByCode(spinner_currency.adapter.getItem(spinner_currency.selectedItemPosition).toString())!!)
                (activity as Navigator).navigateBack()
            }
        }
    }
}
