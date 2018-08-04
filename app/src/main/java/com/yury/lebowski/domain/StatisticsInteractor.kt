package com.yury.lebowski.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.*
import com.yury.lebowski.data.models.Account
import com.yury.lebowski.data.models.Category
import com.yury.lebowski.data.models.Operation
import com.yury.lebowski.data.models.OperationType
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import javax.inject.Inject

object StatisticsInteractor {

    fun getPieChartValues(account: LiveData<Account>, operations : LiveData<List<Operation>>, categories : LiveData<List<Category>>) : LiveData<PieDataSet> {
        val liveData = MutableLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var sum = 0f

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

    fun getLineChartValues(account: LiveData<Account>, operations : LiveData<List<Operation>>, categories : LiveData<List<Category>>) : LiveData<LineDataSet> {
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

    fun getHorizontalBarChartValues(account: LiveData<Account>, operations : LiveData<List<Operation>>, categories : LiveData<List<Category>>) : LiveData<BarDataSet> {
        val liveData = MutableLiveData<BarDataSet>()
        val entries = ArrayList<BarEntry>()
        val color = ArrayList<Int>()
        var sum = 0f

        //val operations = operationRepository.getAllOperations()

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

    // fun getShowLegend() : Boolean {
    // return spref.getShowlegend()
    // }
}