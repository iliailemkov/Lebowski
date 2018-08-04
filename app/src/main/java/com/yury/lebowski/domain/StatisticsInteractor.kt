package com.yury.lebowski.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.*
import com.yury.lebowski.data.models.Account
import com.yury.lebowski.data.models.Category
import com.yury.lebowski.data.models.Operation
import com.yury.lebowski.data.models.OperationType
import com.yury.lebowski.data.repository.BalanceRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import javax.inject.Inject

class StatisticsInteractor @Inject constructor(
        private val balanceRepository : BalanceRepository,
        private val spref : SharedPrefRepository,
        private val operationRepository: OperationRepository
) {

    fun getPieChartValues(accountId : Long) : LiveData<PieDataSet> {
        val liveData = MediatorLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var sum = 0f

        val account = balanceRepository.getBalanceById(accountId)
        val operations = operationRepository.getAllOperations()
        val categories = operationRepository.getAllCategories()

        categories.value?.forEach { category -> operations.value?.filter { el ->
            (el.accountId == account.value?.id) /*and (el == category*/ }?.forEach { t ->
            sum += Math.abs(t.amountInUniversal.toFloat()) }
            if (sum > 0)
                entries.add(PieEntry(sum, category.nameResourceId))
            sum = 0f
        }

        liveData.value = PieDataSet(entries, "")
        liveData.value!!.colors = color
        return liveData
    }

    fun getLineChartValues(accountId : Long) : LiveData<LineDataSet> {
        val liveData = MutableLiveData<LineDataSet>()
        /*val entries = ArrayList<>()
        val color = ArrayList<Int>()
        var sum = 0f

        val operations = operationRepository.getAllOperations()

        operationRepository.getAllCategories().value?.filter { c ->
            if (spref.getOnlyOutcomes()) {
                c.operationType == OperationType.Expenditure
            } else {
                true
            }}!!.forEach { category -> operations.value?.filter { el ->
            (el.accountId == account.id) /*and (el == category*/ }?.forEach { t ->
            sum += Math.abs(t.amountInUniversal.toFloat()) }
            if (sum > 0)
                entries.add(PieEntry(sum, category.nameResourceId))
            sum = 0f
        }

        liveData.value = PieDataSet(entries, "")
        liveData.value!!.colors = color*/
        return liveData
    }

    fun getHorizontalBarChartValues(accountId : Long) : LiveData<BarDataSet> {
        val liveData = MutableLiveData<BarDataSet>()
        val entries = ArrayList<BarEntry>()
        val color = ArrayList<Int>()
        var sum = 0f

        val account = balanceRepository.getBalanceById(accountId)
        val operations = operationRepository.getAllOperations()
        val categories = operationRepository.getAllCategories()

        categories.value?./*filter { c ->
            if (spref.getOnlyOutcomes()) {
                c.operationType == OperationType.Expenditure
            } else {
                true
            }}!!.*/forEach { category -> operations.value?.filter { el ->
            (el.accountId == account.value?.id) /*and (el == category*/ }?.forEach { t ->
            sum += Math.abs(t.amountInUniversal.toFloat()) }
            if (sum > 0)
                entries.add(BarEntry(sum, 1f))
            sum = 0f
        }

        liveData.value = BarDataSet(entries, "")
        liveData.value!!.colors = color
        return liveData
    }

     fun getShowLegend() : Boolean {
        return true//return spref.getShowlegend()
     }
}