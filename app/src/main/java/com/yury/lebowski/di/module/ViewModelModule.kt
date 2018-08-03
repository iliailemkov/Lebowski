package com.yury.lebowski.di.module

import androidx.lifecycle.ViewModel
import com.yury.lebowski.di.ViewModelKey
import com.yury.lebowski.ui.add_operation.AddOperationViewModel
import com.yury.lebowski.ui.home.HomeViewModel
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
    abstract fun bindHAddOperationViewModel(viewModel : AddOperationViewModel) : ViewModel

    /*@Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(view : SettingsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    abstract fun bindTransactionViewModel(view : TransactionViewModel) : ViewModel*/
}