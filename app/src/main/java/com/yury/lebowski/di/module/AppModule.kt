package com.yury.lebowski.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.yury.lebowski.data.dao.AccountDao
import com.yury.lebowski.data.dao.AccountOperationDao
import com.yury.lebowski.data.dao.CategoryDao
import com.yury.lebowski.data.dao.OperationDao
import com.yury.lebowski.data.repository.BalanceRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
import com.yury.lebowski.domain.StatisticsInteractor
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
    fun provideBalanceRepository(accountDao: AccountDao, accountOperationDao: AccountOperationDao) =
            BalanceRepository(accountDao, accountOperationDao)

    @Provides
    @Singleton
    fun provideOperationRepository(operationDao: OperationDao, categoryDao: CategoryDao) =
            OperationRepository(operationDao, categoryDao)

    @Provides
    @Singleton
    fun provideSharedPrefRepository(spref: SharedPreferences): SharedPrefRepository =
            SharedPrefRepository(spref)
}