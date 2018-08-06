package com.yury.lebowski.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.Navigator
import com.yury.lebowski.R
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.ui.about.AboutFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.settings_item)?.isVisible = false
        menu?.findItem(R.id.statistics_item)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.setTitle(R.string.settings)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity?.setTitle(R.string.app_name)
    }
}
