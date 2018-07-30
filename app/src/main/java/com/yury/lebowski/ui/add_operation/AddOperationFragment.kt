package com.yury.lebowski.ui.add_operation

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import com.yury.lebowski.R
import com.yury.lebowski.models.OperationType


private const val OPERATION_TYPE = "operation_type"

class AddOperationFragment : Fragment() {
    private var operationType: OperationType? = null

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
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
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

    private lateinit var viewModel: AddOperationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_operation_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, operationType?.let { AddOperationViewModelFactory(it) }).get(AddOperationViewModel::class.java)
        // TODO: Use the ViewModel
    }


    companion object {
        fun newInstance(operationType: OperationType) = AddOperationFragment().apply {
            arguments = bundleOf(OPERATION_TYPE to operationType)
        }
    }

}