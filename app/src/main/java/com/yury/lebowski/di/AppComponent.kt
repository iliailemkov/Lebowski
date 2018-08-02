package com.example.beardie.currencyholder.di

import android.app.Application
import com.example.beardie.currencyholder.di.module.AppModule
import com.example.beardie.currencyholder.di.module.ViewModelModule
import com.example.beardie.currencyholder.di.module.ViewModule
import com.yury.lebowski.LebowskiApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ViewModule::class,
            ViewModelModule::class
        ]
)
interface AppComponent: AndroidInjector<LebowskiApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application):Builder
        fun build(): AppComponent
    }
}