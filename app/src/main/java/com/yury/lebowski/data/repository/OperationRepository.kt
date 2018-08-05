package com.yury.lebowski.data.repository

import android.database.Observable
import androidx.lifecycle.*
import com.github.mikephil.charting.data.*
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.CategoryDao
import com.yury.lebowski.data.local.dao.OperationDao
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.Operation
import javax.inject.Inject

class OperationRepository @Inject constructor(
        private val accountDao : AccountDao,
        private val operationDao: OperationDao,
        private val categoryDao: CategoryDao
) {
    fun getAllOperations() : LiveData<List<Operation>> = operationDao.getAll()

    fun getOperations(accountId : Long) : LiveData<List<Operation>> = operationDao.findByAccountId(accountId)

    fun getAllCategories() : LiveData<List<Category>> = categoryDao.getAll()

    val s : LiveData<PieDataSet> = Transformations.map(operationDao.getAll()) { input ->
        getPieChartValues(1).value
    }

    fun getPieChartValues(accountId : Long) : LiveData<PieDataSet> {
        val liveData = MediatorLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var sum = 0f

        val operations = operationDao.getAll()

        categoryDao.getAll().value?.forEach { category -> operations.value?.filter { el ->
            (el.accountId == accountId) and (el.categoryId == category.id)
        }?.forEach { t ->
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

        val operations = operationDao.getAll()

        /*categories.value?./*filter { c ->
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
        liveData.value!!.colors = color*/
        return liveData
    }

    fun getShowLegend() : Boolean {
        return true//return spref.getShowlegend()
    }


}