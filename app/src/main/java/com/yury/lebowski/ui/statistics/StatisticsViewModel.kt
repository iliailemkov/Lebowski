package com.yury.lebowski.ui.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.yury.lebowski.data.local.models.OperationWrapper
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import java.util.*
import javax.inject.Inject


class StatisticsViewModel @Inject constructor(
        private val accountRepository: AccountRepository,
        private val spref: SharedPrefRepository,
        private val operationRepository: OperationRepository
) : ViewModel() {

    var startDate: Date? = null
    var finishDate: Date? = null
    var accountId: Long? = null

    var currentBalance = MutableLiveData<List<OperationWrapper>>()
        set(value) {
            currentBalance.value = value.value
        }

    val pieSummary = Transformations.switchMap(currentBalance) { it -> getPieChartValues(accountId?:1, it) }

    private val categoriesRepData = Observer<List<OperationWrapper>> { res ->
        if (res !== null) {
            currentBalance.value = res
        }
    }

    fun getPieChartValues(accountId: Long, operations: List<OperationWrapper>): LiveData<PieDataSet> {
        val liveData = MutableLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var map: HashMap<String, Float?> = HashMap()

        operations.forEach { operation ->
            if(startDate!!.before(operation.date) && finishDate!!.after(operation.date)) {
                if (map.contains(operation.categoryName)) {
                    map[operation.categoryName] = map[operation.categoryName]?.plus(Math.abs(operation.amount.toFloat()))
                } else {
                    map.put(operation.categoryName, operation.amount.toFloat())
                }
            }
        }
        map.forEach { v ->
            entries.add(PieEntry(v.value ?: 0f, v.key))
        }

        liveData.value = PieDataSet(entries, "")
        liveData.value!!.colors = color
        return liveData
    }

    fun getLineChartValues(accountId: Long, operations: List<OperationWrapper>): LiveData<LineDataSet> {
        val liveData = MutableLiveData<LineDataSet>()
        return liveData
    }

    fun getShowLegend(): Boolean {
        return true
    }

    fun initFilterOperations(startDate: Date, finishDate: Date, accountId: Long) {
        this.startDate = startDate
        this.finishDate = finishDate
        this.accountId = accountId
        operationRepository.getWrapperOperationsByDate(startDate, finishDate, accountId).observeForever(categoriesRepData)
    }

    override fun onCleared() {
        super.onCleared()
        operationRepository.getWrapperOperationsByDate(startDate!!, finishDate!!, accountId!!).observeForever(categoriesRepData)
    }
}