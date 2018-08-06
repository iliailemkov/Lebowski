package com.yury.lebowski.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.db.LebowskiDb
import com.yury.lebowski.data.local.models.Account
import com.yury.lebowski.data.local.models.enums.CurrencyType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.rules.TestRule


@RunWith(AndroidJUnit4::class)
class AccountDaoTest {

    @JvmField @Rule var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var accountDao: AccountDao
    private lateinit var db: LebowskiDb

    private val accounts = listOf(
            Account(1,
                    "Card",
                    0.0,
                    CurrencyType.Ruble),
            Account(2,
                    "Cash",
                    0.0,
                    CurrencyType.Dollar))

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, LebowskiDb::class.java!!).build()
        accountDao = db.accountDao()
        db.accountDao().insertAll(accounts)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getAll() {
        val liveData = accountDao.getAll()
        assertEquals(getBlockValue(liveData), accounts)
    }

    @Test
    fun getById() {
        val account = accountDao.findById(1)
        assertEquals(account, accounts[0])
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