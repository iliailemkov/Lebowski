package com.yury.lebowski.ui.get_summary

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yury.lebowski.R
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.ui.statistics.StatisticsFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.get_summary_fragment.*
import java.util.*
import javax.inject.Inject


@NavigatorMainContainer
class GetSummaryFragment @Inject constructor(

) : DaggerFragment() {

    private var startDateTime = Calendar.getInstance()
    private var finishDateTime = Calendar.getInstance()

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

    private fun initDateTimePicker() {
        start_date.setOnClickListener {
            DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                {
                    startDateTime.set(Calendar.YEAR, year)
                    startDateTime.set(Calendar.MONTH, monthOfYear)
                    startDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
            }, startDateTime.get(Calendar.YEAR), startDateTime.get(Calendar.MONTH), startDateTime.get(Calendar.DAY_OF_MONTH)).show()
            //start_date.setText(DateUtils.formatDateTime(activity, startDateTime.timeInMillis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_DATE))
        }
        finish_date.setOnClickListener {
            DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                {
                    finishDateTime.set(Calendar.YEAR, year)
                    finishDateTime.set(Calendar.MONTH, monthOfYear)
                    finishDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                //finish_date.setText(DateUtils.formatDateTime(activity, finishDateTime.timeInMillis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_DATE))
            }, finishDateTime.get(Calendar.YEAR), finishDateTime.get(Calendar.MONTH), finishDateTime.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun initPlotBtn() {
        plot_summary_button.setOnClickListener(View.OnClickListener {
            (activity as Navigator).navigateTo(StatisticsFragment.newInstance(1), "NavigateToStatistic")
        })
    }
}