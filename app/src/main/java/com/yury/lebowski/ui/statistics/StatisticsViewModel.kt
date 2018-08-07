package com.yury.lebowski.ui.statistics

import androidx.lifecycle.*
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import javax.inject.Inject


class StatisticsViewModel @Inject constructor(
        private val accountRepository: AccountRepository,
        private val spref: SharedPrefRepository,
        private val operationRepository: OperationRepository
) : ViewModel() {

    var currentBalance = MutableLiveData<List<Operation>>()
        set(value) {
            currentBalance.value = value.value
        }

    val pieSummary = Transformations.switchMap(currentBalance) { it -> getPieChartValues(1, it) }

    val lineSummary = Transformations.switchMap(currentBalance) { it -> getLineChartValues(1, it) }

    val barSummary = Transformations.switchMap(currentBalance) { it -> getHorizontalBarChartValues(1, it) }

    private val categoriesRepData = Observer<List<Operation>> { res ->
        if (res !== null) {
            currentBalance.value = res
        }
    }

    fun getPieChartValues(accountId: Long, operations: List<Operation>): LiveData<PieDataSet> {
        val liveData = MutableLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var map: HashMap<String, Float?> = HashMap()

        operations.forEach { operation ->
            if (map.contains(operation.categoryId.toString())) {
                if (map[operation.categoryId.toString()] == null) {
                    map[operation.categoryId.toString()] = 0f
                }
                map[operation.categoryId.toString()] = map[operation.categoryId.toString()]?.plus(Math.abs(operation.amount.toFloat()))
            } else {
                map.put(operation.categoryId.toString(), operation.amount.toFloat())
            }
        }
        map.forEach { v ->
            entries.add(PieEntry(v.value ?: 0f, v.key))
        }

        liveData.value = PieDataSet(entries, "")
        liveData.value!!.colors = color
        return liveData
    }

    fun getLineChartValues(accountId: Long, operations: List<Operation>): LiveData<LineDataSet> {
        val liveData = MutableLiveData<LineDataSet>()
        return liveData
    }

    fun getHorizontalBarChartValues(accountId: Long, operations: List<Operation>): LiveData<BarDataSet> {
        val liveData = MutableLiveData<BarDataSet>()
        return liveData
    }

    fun getShowLegend(): Boolean {
        return true
    }

    init {
        operationRepository.getAllOperations().observeForever(categoriesRepData)
    }

    override fun onCleared() {
        super.onCleared()
        operationRepository.getAllOperations().removeObserver(categoriesRepData)
    }
}