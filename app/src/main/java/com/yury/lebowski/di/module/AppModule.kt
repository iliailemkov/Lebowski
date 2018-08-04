package com.yury.lebowski.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.yury.lebowski.data.local.dao.AccountDao
import com.yury.lebowski.data.local.dao.AccountOperationDao
import com.yury.lebowski.data.local.dao.CategoryDao
import com.yury.lebowski.data.local.dao.OperationDao
import com.yury.lebowski.data.repository.AccountRepository
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
    fun provideAccountRepository(accountDao: AccountDao, accountOperationDao: AccountOperationDao) =
            AccountRepository(accountDao, accountOperationDao)

    @Provides
    @Singleton
    fun provideOperationRepository(accountDao : AccountDao, operationDao: OperationDao, categoryDao: CategoryDao) =
            OperationRepository(accountDao, operationDao, categoryDao)

    @Provides
    @Singleton
    fun provideSharedPrefRepository(spref: SharedPreferences): SharedPrefRepository =
            SharedPrefRepository(spref)
}