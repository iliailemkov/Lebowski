package com.yury.lebowski.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.AccountOperationDao
import com.yury.lebowski.data.local.db.LebowskiDb
import com.yury.lebowski.data.local.models.Account
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
class AccountOperationDaoTest {

    @JvmField @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var accountDao: AccountDao
    private lateinit var accountOperationDao: AccountOperationDao
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

    private val operations = listOf(Operation(1, Date(), OperationType.Income, OperationState.Normal,999.0, 1, 1))

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, LebowskiDb::class.java!!).build()
        accountDao = db.accountDao()
        accountOperationDao = db.accountOperationDao()
        db.accountDao().insertAll(accounts)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertOperationAndUpdateAmount() {
        var startBalance = accounts[0].balance
        accountOperationDao.insertOperationAndUpdateAmount(operations[0], operations[0].amount, accounts[0].id!!)
        startBalance += operations[0].amount
        Assert.assertEquals(startBalance.toFloat(), accountDao.findById(1).balance.toFloat())
    }

    private fun <T> getBlockValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)//To change body of created functions use File | Settings | File Templates.
            }

        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T

    }
}