package com.yury.lebowski.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.yury.lebowski.data.local.dao.*
import com.yury.lebowski.data.repository.AccountRepository
import com.yury.lebowski.data.repository.OperationRepository
import com.yury.lebowski.data.repository.SharedPrefRepository
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
    fun provideAccountRepository(accountDao: AccountDao, accountOperationDao: AccountOperationDao, exchangeRateDao: ExchangeRateDao) =
            AccountRepository(accountDao, accountOperationDao, exchangeRateDao)

    @Provides
    @Singleton
    fun provideOperationRepository(accountDao: AccountDao,
                                   operationDao: OperationDao,
                                   categoryDao: CategoryDao,
                                   periodicalOperationDao: PeriodicalOperationDao) =
            OperationRepository(accountDao, operationDao, categoryDao, periodicalOperationDao)

    @Provides
    @Singleton
    fun provideSharedPrefRepository(spref: SharedPreferences): SharedPrefRepository =
            SharedPrefRepository(spref)
}