package com.yury.lebowski.ui.statistics

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.mikephil.charting.data.PieDataSet
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
        app: Application,
        private val accountRepository : AccountRepository,
        //private val statisticsInteractor: StatisticsInteractor,
        private val spref : SharedPrefRepository,
        private val operationRepository: OperationRepository
): AndroidViewModel(app) {

    val pieSummary : LiveData<PieDataSet> = operationRepository.s

    val categories = operationRepository.getAllCategories()

    val lineSummary by lazy {
        operationRepository.getLineChartValues(1)
    }

    val barSummary by lazy {
        operationRepository.getHorizontalBarChartValues(1)
    }

    fun getShowLegend() = operationRepository.getShowLegend()
}