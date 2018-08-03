package com.yury.lebowski.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.yury.lebowski.data.dao.AccountDao
import com.yury.lebowski.data.repository.BalanceRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideBalanceRepository(accountDao: AccountDao) = BalanceRepository(accountDao)

    /*@Provides
    @Singleton
    fun provideSharedPrefRepository(spref: SharedPreferences): SharedPrefRepository =
            SharedPrefRepository(spref)*/
}