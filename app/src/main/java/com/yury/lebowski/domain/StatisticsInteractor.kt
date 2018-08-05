package com.yury.lebowski.domain

import android.database.Observable
import android.os.AsyncTask
import androidx.lifecycle.*
import com.github.mikephil.charting.data.*
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.OperationType
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import javax.inject.Inject
import androidx.lifecycle.LiveData



class StatisticsInteractor @Inject constructor(
        private val accountRepository : AccountRepository,
        private val spref : SharedPrefRepository,
        private val operationRepository: OperationRepository
) {

    //val user = Transformations.switchMap(getPieChartValues(1)) { id -> getUser(id) }
    //val r : LiveData<PieDataSet> = Transformations.map(r, r -> getPieChartValues(1)

    internal class ChartLiveData(
            private val sourceLiveData: LiveData<List<Operation>>
    ) : LiveData<PieDataSet>(), Observer<List<Operation>> {

        override fun onActive() {
            sourceLiveData.observeForever(this)
        }

        override fun onInactive() {
            sourceLiveData.removeObserver(this)
        }

        override fun onChanged(dbRows: List<Operation>?) {
            // set up a background thread to complete the transformation

            /*AsyncTask.execute {
                assert(dbRows != null)
                val myRichObjects = LinkedList()
                for (myDBRow in myDBRows) {
                    myRichObjects.add(MyRichObjectBuilder.from(myDBRow).build())
                }
                // use LiveData method postValue (rather than setValue) on
                // background threads
                postValue(myRichObjects)
            }*/

        }
    }

    fun getPieChartValues(accountId : Long) : LiveData<PieDataSet> {
        val liveData = MediatorLiveData<PieDataSet>()
        val entries = ArrayList<PieEntry>()
        val color = ArrayList<Int>()
        var sum = 0f

        val s : Observable<Category>

        val account = accountRepository.getBalanceById(accountId)
        val operations = operationRepository.getAllOperations()
        //val categories = operationRepository.getAllCategories()

        operationRepository.getAllCategories().value?.forEach { category -> operations.value?.filter { el ->
            (el.accountId == account.value?.id) and (el.categoryId == category.id)
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

        val account = accountRepository.getBalanceById(accountId)
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