package com.yury.lebowski.di.module

import com.yury.lebowski.MainActivity
import com.yury.lebowski.ui.about.AboutFragment
import com.yury.lebowski.ui.add_account.AddAccountFragment
import com.yury.lebowski.ui.add_operation.AddOperationFragment
import com.yury.lebowski.ui.get_summary.GetSummaryFragment
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.operations.OperationsFragment
import com.yury.lebowski.ui.settings.SettingsFragment
import com.yury.lebowski.ui.statistics.StatisticsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun mainACtivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun operationsFrgament(): OperationsFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun aboutFragment(): AboutFragment

    @ContributesAndroidInjector
    abstract fun addOperationFragment(): AddOperationFragment

    @ContributesAndroidInjector
    abstract fun getSummaryFragment(): GetSummaryFragment

    @ContributesAndroidInjector
    abstract fun statisticsFragment(): StatisticsFragment

    @ContributesAndroidInjector
    abstract fun addAccountFragment(): AddAccountFragment
}