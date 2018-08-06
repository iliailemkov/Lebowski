package com.yury.lebowski.ui.add_operation

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.Navigator
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.*
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.di.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_operation_fragment.*
import java.util.*
import javax.inject.Inject
import android.widget.CompoundButton


class AddOperationFragment : DaggerFragment(), View.OnFocusChangeListener {

    val OPERATION_TYPE = "operation_type"

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var operationType: OperationType? = null
    private lateinit var viewModel: AddOperationViewModel

    companion object {
        fun newInstance(operationType: OperationType) = AddOperationFragment().apply {
            arguments = bundleOf(OPERATION_TYPE to operationType)
        }
    }

    private val categoryList: Observer<List<Category>> = Observer { res ->
        if(res != null) {
            categories.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    private val accountList: Observer<List<Account>> = Observer { res ->
        if(res != null) {
            accounts.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, res.map { l -> l.name })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            operationType = it.get(OPERATION_TYPE) as OperationType
        }
        operationType?.let {
            activity?.setTitle(when (it) {
                OperationType.Income -> R.string.add_income
                OperationType.Expenditure -> R.string.add_expenditure
            })
        }
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.settings_item)?.isVisible = false
        menu?.findItem(R.id.statistics_item)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            (activity as Navigator).navigateBack()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity?.setTitle(R.string.app_name)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_operation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddOperationViewModel::class.java)
        spinner_currency.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, CurrencyType.values().map { c -> c.code })
        initAddButton()
        initPeriodic()
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
        add_button.setOnClickListener {
            try {
                addOperation()
            } catch (e : Exception) {
                Toast.makeText(context, R.string.incorrect_data, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFocusChange(view: View?, condition: Boolean) {
    }

    private fun initPeriodic() {
        switch_periodic.isChecked = false
        operation_preiodic_layout.visibility = View.GONE
        switch_periodic.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            operation_preiodic_layout.visibility = if(isChecked) View.VISIBLE else View.GONE
        })
    }

    private fun addOperation() {
        if(switch_periodic.isChecked) {
            viewModel.addPeriodicOperation(Operation(null,
                    Date(),
                    operationType!!,
                    moneyEditText.text.toString().toDouble(),
                    accounts.adapter.getItemId(accounts.selectedItemId.toInt()),
                    categories.adapter.getItemId(categories.selectedItemId.toInt())), 1,
                    operation_preiodic_input.text.toString().toLong(),
                    CurrencyType.findByCode(spinner_currency.adapter.getItem(spinner_currency.selectedItemPosition).toString())!!)
            Toast.makeText(activity, getString(R.string.successfully_added), Toast.LENGTH_SHORT).show()
            (activity as Navigator).navigateBack()
        } else {
            viewModel.addOperation(
                    Operation(null,
                            Date(),
                            operationType!!,
                            moneyEditText.text.toString().toDouble(),
                            accounts.adapter.getItemId(accounts.selectedItemId.toInt()),
                            categories.adapter.getItemId(categories.selectedItemId.toInt())),
                            CurrencyType.findByCode(spinner_currency.adapter.getItem(spinner_currency.selectedItemPosition).toString())!!)
            Toast.makeText(activity, getString(R.string.successfully_added), Toast.LENGTH_SHORT).show()
            (activity as Navigator).navigateBack()
        }
    }
}
