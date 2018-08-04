package com.yury.lebowski.domain

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.PieDataSet
import com.yury.lebowski.data.local.models.Category


class ChartLiveData(
        private val sourceLiveData: LiveData<List<Category>>
) : LiveData<PieDataSet>(), Observer<List<Category>> {

    override fun onActive() {
        sourceLiveData.observeForever(this)
    }

    override fun onInactive() {
        sourceLiveData.removeObserver(this)
    }

    override fun onChanged(dbRows: List<Category>?) {
        // set up a background thread to complete the transformation
       /* AsyncTask.execute {
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