package com.yury.lebowski.ui.get_summary

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import com.yury.lebowski.R
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.ui.statistics.StatisticsFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.get_summary_fragment.*
import java.util.*
import javax.inject.Inject
import java.text.SimpleDateFormat


@NavigatorMainContainer
class GetSummaryFragment @Inject constructor(

) : DaggerFragment() {

    private val myFormat = "dd MMMM yyyy"

    private var calendar = Calendar.getInstance()
    private var startDate : Date? = null
    private var finishDate : Date? = null

    companion object {
        fun newInstance(): GetSummaryFragment {
            return GetSummaryFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.get_summary_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(R.string.statisitcs, R.dimen.toolbar_elevation, this)
        initDateTimePicker();
        initPlotBtn()
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
        plot_summary_button.setOnClickListener(View.OnClickListener {
            (activity as Navigator).navigateTo(StatisticsFragment.newInstance(1, startDate!!.time, finishDate!!.time), "NavigateToStatistic")
        })
    }
}