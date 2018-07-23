package com.yury.lebowski.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.MainActivity
import com.yury.lebowski.R
import com.yury.lebowski.databinding.HomeFragmentBinding
import com.yury.lebowski.util.autoCleared


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var binding: HomeFragmentBinding by autoCleared<HomeFragmentBinding>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.home_fragment,
                container,
                false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(this)
    }
}
