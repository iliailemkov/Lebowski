package com.yury.lebowski.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.yury.lebowski.R
import com.yury.lebowski.di.ViewModelFactory
import com.yury.lebowski.navigation.NavigatiorDetailContainer
import com.yury.lebowski.navigation.Navigator
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_statistic.*
import javax.inject.Inject

@NavigatiorDetailContainer
class StatisticsFragment : DaggerFragment() {

    val ACCOUNT_ID = "account_id"

    companion object {
        fun newInstance(accountId: Long) = StatisticsFragment().apply {
            arguments = bundleOf(ACCOUNT_ID to accountId)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: StatisticsViewModel

    private val dataPieSet: Observer<PieDataSet> = Observer { res ->
        if (res != null) {
            val dataSet = viewModel.pieSummary.value
            dataSet!!.sliceSpace = 8f
            dataSet.selectionShift = 8f
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            dataSet.setDrawValues(true)
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.BLACK)
            pie_chart.data = data
            pie_chart.isHighlightPerTapEnabled = true
            pie_chart.highlightValues(null)
            pie_chart.invalidate()
        }
    }

    private val dataLineSet: Observer<LineDataSet> = Observer { res ->
        if (res != null) {
            val dataSet = viewModel.lineSummary.value
            //dataSet!!.sliceSpace = 8f
            //dataSet.selectionShift = 8f
            //dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
            //dataSet.setDrawValues(true)
            /*val data = LineDataSet()
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.BLACK)
            line_chart.data = data
            line_chart.isHighlightPerTapEnabled = true
            line_chart.highlightValues(null)
            line_chart.invalidate()*/
        }
    }

    private val dataBarSet: Observer<BarDataSet> = Observer { res ->
        if (res != null) {
            val dataSet = viewModel.barSummary.value
            //dataSet!!. = 8f
            //dataSet.selectionShift = 8f
            dataSet?.colors = ColorTemplate.MATERIAL_COLORS.toList()
            dataSet?.setDrawValues(true)
            val data = BarData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.BLACK)
            horizontal_bar_chart.data = data
            horizontal_bar_chart.isHighlightPerTapEnabled = true
            horizontal_bar_chart.highlightValues(null)
            horizontal_bar_chart.invalidate()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as Navigator).initToolbar(R.string.statisitcs, R.dimen.toolbar_elevation, this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StatisticsViewModel::class.java)
        initPieChart()

    }

    override fun onStart() {
        super.onStart()
        viewModel.pieSummary.observe(this, dataPieSet)
        viewModel.lineSummary.observe(this, dataLineSet)
        viewModel.barSummary.observe(this, dataBarSet)
    }

    override fun onStop() {
        super.onStop()
        viewModel.pieSummary.removeObservers(this)
        viewModel.lineSummary.removeObservers(this)
        viewModel.barSummary.removeObservers(this)
    }

    private fun initPieChart() {
        pie_chart.legend.isEnabled = viewModel.getShowLegend()
        pie_chart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pie_chart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        pie_chart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        pie_chart.description.isEnabled = false

        pie_chart.isDrawHoleEnabled = true
        pie_chart.holeRadius = 30f
        pie_chart.transparentCircleRadius = 40f
        pie_chart.setHoleColor(Color.WHITE)
        pie_chart.setTransparentCircleColor(Color.WHITE)
        pie_chart.setTransparentCircleAlpha(90)

        pie_chart.setUsePercentValues(true)
        pie_chart.setExtraOffsets(8f, 8f, 8f, 8f)
        pie_chart.dragDecelerationFrictionCoef = 0.95f
        pie_chart.rotationAngle = 0f
        pie_chart.isRotationEnabled = true
        pie_chart.isHighlightPerTapEnabled = true
        pie_chart.animateY(1000, Easing.EasingOption.EaseInOutQuad)
        pie_chart.setEntryLabelColor(Color.BLACK)
        pie_chart.setEntryLabelTextSize(14f)

        //pie_chart.setOnChartValueSelectedListener(this)
    }


}