package com.yury.lebowski.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.R
import com.yury.lebowski.data.local.dao.OperationDao
import com.yury.lebowski.data.local.db.LebowskiDb
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.Category
import com.yury.lebowski.data.local.models.Operation
import com.yury.lebowski.data.local.models.enums.CurrencyType
import com.yury.lebowski.data.local.models.enums.OperationState
import com.yury.lebowski.data.local.models.enums.OperationType
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class OperationDaoTest {
    @JvmField @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var operationDao : OperationDao
    private lateinit var db: LebowskiDb

    private var accounts = listOf(
            Account(1,
                    "Card",
                    0.0,
                    CurrencyType.Ruble),
            Account(2,
                    "Cash",
                    0.0,
                    CurrencyType.Dollar))

    private val categories = listOf(Category(1, OperationType.Expenditure, LebowskiApplication.instance.getString(R.string.vehicle)))

    private val operations = listOf(Operation(1, Date(), OperationType.Income, OperationState.Normal,999.0, 1, 1))

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, LebowskiDb::class.java!!).build()
        operationDao = db.operationDao()
        db.operationDao().insertAll(operations)
        db.accountDao().insertAll(accounts)
        db.categoryDao().insertAll(categories)
    }

    @Test
    fun getAll() {
        val liveData = operationDao.getAll()
        Assert.assertEquals(getBlockValue(liveData), operations)
    }

    @Test
    fun getById() {
        val operation = operationDao.findById(1)
        Assert.assertEquals(operation, operations[0])
    }

    @After
    fun closeDb() {
        db.close()
    }

    private fun <T> getBlockValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }

        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }
}