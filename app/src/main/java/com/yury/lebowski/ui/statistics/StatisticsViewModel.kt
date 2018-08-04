package com.yury.lebowski.ui.statistics

import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.repository.BalanceRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import com.yury.lebowski.domain.StatisticsInteractor
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
        private val balanceRepository : BalanceRepository,
        //private val statisticsInteractor: StatisticsInteractor,
        private val spref : SharedPrefRepository,
        private val operationRepository: OperationRepository
): ViewModel() {

    val pieSummary by lazy {
        StatisticsInteractor.getPieChartValues(balanceRepository.getBalanceById(1),
                operationRepository.getAllOperations(),
                operationRepository.getAllCategories())
    }

    val lineSummary by lazy {
        StatisticsInteractor.getLineChartValues(balanceRepository.getBalanceById(1),
                operationRepository.getAllOperations(),
                operationRepository.getAllCategories())
    }

    val barSummary by lazy {
        StatisticsInteractor.getHorizontalBarChartValues(balanceRepository.getBalanceById(1),
                operationRepository.getAllOperations(),
                operationRepository.getAllCategories())
    }

    fun getShowLegend() = true

}