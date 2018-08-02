package com.example.beardie.currencyholder.di.module

import com.yury.lebowski.MainActivity
import com.yury.lebowski.ui.about.AboutFragment
import com.yury.lebowski.ui.add_operation.AddOperationFragment
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun mainACtivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun homeFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun settingsFragment() : SettingsFragment

    @ContributesAndroidInjector
    abstract fun aboutFragment() : AboutFragment

    @ContributesAndroidInjector
    abstract fun addOperationFragment() : AddOperationFragment
}