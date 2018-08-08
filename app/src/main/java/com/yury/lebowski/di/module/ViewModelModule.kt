package com.yury.lebowski.di.module

import androidx.lifecycle.ViewModel
import com.yury.lebowski.di.ViewModelKey
import com.yury.lebowski.ui.add_account.AddAccountViewModel
import com.yury.lebowski.ui.add_operation.AddOperationViewModel
import com.yury.lebowski.ui.home.HomeViewModel
import com.yury.lebowski.ui.operations.OperationsViewModel
import com.yury.lebowski.ui.settings.SettingsViewModel
import com.yury.lebowski.ui.statistics.StatisticsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel : HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddOperationViewModel::class)
    abstract fun bindAddOperationViewModel(viewModel : AddOperationViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OperationsViewModel::class)
    abstract fun bindOperationsViewModel(view : OperationsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    abstract fun bindStatisticsViewModel(viewModel : StatisticsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(view : SettingsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAccountViewModel::class)
    abstract fun bindAddAccountViewModel(view : AddAccountViewModel) : ViewModel
}