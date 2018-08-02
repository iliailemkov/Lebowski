package com.yury.lebowski.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yury.lebowski.data.dao.AccountDao
import com.yury.lebowski.data.dao.CategoryDao
import com.yury.lebowski.data.models.Account

@Database(entities = [(Account::class)], version = 1, exportSchema = false)
abstract class LebowskiDb : RoomDatabase() {
    companion object {
        private var INSTANCE: LebowskiDb? = null
        fun getDataBase(context: Context): LebowskiDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, LebowskiDb::class.java, "lebowski-db").allowMainThreadQueries().build()
            }
            return INSTANCE as LebowskiDb
        }
    }

    abstract fun accountDao() : AccountDao
    abstract fun categoryDao() : CategoryDao
}