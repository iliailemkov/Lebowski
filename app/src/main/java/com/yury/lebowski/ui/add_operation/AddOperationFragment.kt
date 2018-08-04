package com.yury.lebowski.ui.add_operation

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.Navigator
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.R
import com.yury.lebowski.data.models.CurrencyType
import com.yury.lebowski.data.models.Operation
import com.yury.lebowski.data.models.OperationType
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_operation_fragment.*
import java.util.*
import javax.inject.Inject


class AddOperationFragment : DaggerFragment() {

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
        initAddButton()
    }

    private fun initAddButton() {
        add_button.setOnClickListener {
            viewModel.addOperation(Operation(4, Date(), CurrencyType.Ruble, OperationType.Income, 10.0, 10.0, 1, 2))
            Toast.makeText(activity, getString(R.string.successfully_added), Toast.LENGTH_SHORT).show()
            (activity as Navigator).navigateBack()
        }
    }
}
