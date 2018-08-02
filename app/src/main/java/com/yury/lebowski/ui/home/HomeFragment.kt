package com.yury.lebowski.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.beardie.currencyholder.di.ViewModelFactory
import com.yury.lebowski.R
import com.yury.lebowski.databinding.HomeFragmentBinding
import com.yury.lebowski.models.OperationType
import com.yury.lebowski.util.data_binding.autoCleared
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
    private var binding: HomeFragmentBinding by autoCleared<HomeFragmentBinding>()
    private var listener: HomeFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.home_fragment,
                container,
                false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        speedDial.inflate(R.menu.menu_speed_dial)
        speedDial.setOnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.manage_categories -> {
                    Toast.makeText(context, getString(R.string.tbd), Toast.LENGTH_SHORT).show()
                    return@setOnActionSelectedListener true
                }
                R.id.add_income -> listener?.onAddOperationClicked(OperationType.Income)
                R.id.add_expenditure -> listener?.onAddOperationClicked(OperationType.Expenditure)
            }
            false
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement HomeFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings();
    }

    interface HomeFragmentListener {
        fun onAddOperationClicked(operationType: OperationType)
    }
}
