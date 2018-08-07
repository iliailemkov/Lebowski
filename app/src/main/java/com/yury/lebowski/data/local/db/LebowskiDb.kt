package com.yury.lebowski.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yury.lebowski.data.local.converters.CurrencyTypeConverter
import com.yury.lebowski.data.local.converters.DateConverter
import com.yury.lebowski.data.local.converters.OperationTypeConverter
import com.yury.lebowski.data.local.dao.*
import com.yury.lebowski.data.local.models.*
import java.util.concurrent.Executors


@Database(entities =
[Account::class,
    Category::class,
    Operation::class,
    PeriodicalOperation::class,
    ExchangeRate::class],
        version = 1,
        exportSchema = false)
@TypeConverters(value =
[DateConverter::class,
    OperationTypeConverter::class,
    CurrencyTypeConverter::class])
abstract class LebowskiDb : RoomDatabase() {
    companion object {
        private var INSTANCE: LebowskiDb? = null

        @Synchronized
        fun getInstance(context: Context): LebowskiDb {
            if (INSTANCE == null) {
                INSTANCE = getDataBase(context)
            }
            return INSTANCE!!
        }

        fun getDataBase(context: Context): LebowskiDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, LebowskiDb::class.java, "lebowski-db")
                        .allowMainThreadQueries()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadScheduledExecutor().execute(Runnable {
                                    getInstance(context).accountDao().insertAll(PrepopulateData.accounts)
                                    getInstance(context).categoryDao().insertAll(PrepopulateData.categories)
                                    getInstance(context).exchangeRateDao().insertAll(PrepopulateData.rates)
                                    //(context).operationDao().insertAll(PrepopulateData.operations)
                                    //getInstance(context).periodicalOperationDao().insertAll(PrepopulateData.periodicalOperation)
                                })
                            }
                        })
                        .build()

            }
            return INSTANCE as LebowskiDb
        }
    }

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun operationDao(): OperationDao
    abstract fun accountOperationDao(): AccountOperationDao
    abstract fun periodicalOperationDao(): PeriodicalOperationDao
    abstract fun exchangeRateDao(): ExchangeRateDao
}