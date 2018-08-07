package com.yury.lebowski.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.R
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.ui.about.AboutFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@NavigatorMainContainer
class SettingsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SettingsViewModel

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(R.string.settings, R.dimen.toolbar_elevation, this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
        switch_summary.isChecked = viewModel.getSummaryBoolean()
        switch_summary.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setSummaryBoolean(b)
        }
        switch_legend.isChecked = viewModel.getShowLegend()
        switch_legend.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setShowLegend(b)
        }
        tv_license.setOnClickListener { view ->
            AlertDialog.Builder(context!!).setMessage(R.string.mit_license).setTitle("License").create().show()
        }
        tv_about.setOnClickListener { view ->
            (activity as Navigator).navigateTo(AboutFragment.newInstance(), "NavigteToAboutFragment")
        }
        tv_feedback.setOnClickListener { view ->
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "iliailemkov@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Where's my money?")
            startActivity(Intent.createChooser(emailIntent, "Send email"))
        }
    }
}
