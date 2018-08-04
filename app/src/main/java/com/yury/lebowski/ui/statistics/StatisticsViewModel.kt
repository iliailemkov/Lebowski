package com.yury.lebowski.ui.statistics

import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieDataSet
import com.yury.lebowski.data.models.Account
import com.yury.lebowski.data.repository.BalanceRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import com.yury.lebowski.domain.StatisticsInteractor
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
        private val balanceRepository : BalanceRepository,
        private val statisticsInteractor: StatisticsInteractor,
        private val spref : SharedPrefRepository,
        private val operationRepository: OperationRepository
): ViewModel() {

    val pieSummary by lazy {
        statisticsInteractor.getPieChartValues(1)
    }

    val lineSummary by lazy {
        statisticsInteractor.getLineChartValues(1)
    }

    val barSummary by lazy {
        statisticsInteractor.getHorizontalBarChartValues(1)
    }

    fun getShowLegend() = statisticsInteractor.getShowLegend()
}