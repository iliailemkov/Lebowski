package com.yury.lebowski.ui.get_summary

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.yury.lebowski.R
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.ui.CustomSpinnerAdapter
import com.yury.lebowski.ui.statistics.StatisticsFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.get_summary_fragment.*
import java.util.*
import javax.inject.Inject
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.ui.add_operation.AddOperationViewModel
import kotlinx.android.synthetic.main.add_account_fragment.*


@NavigatorMainContainer
class GetSummaryFragment @Inject constructor(

) : DaggerFragment() {

    private val myFormat = "dd MMMM yyyy"

    private var calendar = Calendar.getInstance()
    private var startDate : Date? = null
    private var finishDate : Date? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: GetSummaryViewModel

    companion object {
        fun newInstance(): GetSummaryFragment {
            return GetSummaryFragment()
        }
    }

    var startDatePicker: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {

        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                      dayOfMonth: Int) {
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            startDate = Date(calendar.timeInMillis)
            updateLabel(start_date)
        }
    }

    var finishDatePicker: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {

        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            finishDate = Date(calendar.timeInMillis)
            updateLabel(finish_date)
        }
    }

    private val accountList: Observer<List<Account>> = Observer { res ->
        if (res != null) {
            get_summary_accounts.adapter = CustomSpinnerAdapter(context!!, res)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.get_summary_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GetSummaryViewModel::class.java)
        (activity as Navigator).initToolbar(R.string.statisitcs, R.dimen.toolbar_elevation, this)
        initDateTimePicker();
        initPlotBtn()
    }

    override fun onStart() {
        super.onStart()
        viewModel.accounts.observe(this, accountList)
    }

    override fun onStop() {
        super.onStop()
        viewModel.accounts.removeObservers(this)
    }

    private fun updateLabel(et : EditText) {
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        et.setText(sdf.format(calendar.getTime()))
    }

    private fun initDateTimePicker() {
        start_date.setOnClickListener {
            DatePickerDialog(context, startDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        finish_date.setOnClickListener {
            DatePickerDialog(context, finishDatePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun initPlotBtn() {
        plot_summary_button.setOnClickListener {
            if(validateStartDate() && validateFinishDate()) {
                (activity as Navigator).navigateTo(StatisticsFragment.newInstance((get_summary_accounts.selectedItem as Account).id!!, startDate!!.time, finishDate!!.time), "NavigateToStatistic")
            }
        }
    }

    private fun validateStartDate() : Boolean {
        if (!TextUtils.isEmpty(start_date.text)) {
            til_start_date.setError(null)
        }
        else {
            til_start_date.setError(getString(R.string.summary_incorrect_value))
        }
        return !TextUtils.isEmpty(start_date.text)
    }

    private fun validateFinishDate() : Boolean {
        if (!TextUtils.isEmpty(finish_date.text)) {
            til_finish_date.setError(null)
        }
        else {
            til_finish_date.setError(getString(R.string.summary_incorrect_value))
        }
        return !TextUtils.isEmpty(finish_date.text)
    }
}