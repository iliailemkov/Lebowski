package com.yury.lebowski.di

import android.app.Application
import com.yury.lebowski.di.module.AppModule
import com.yury.lebowski.di.module.ViewModelModule
import com.yury.lebowski.di.module.ViewModule
import com.yury.lebowski.LebowskiApplication
import com.yury.lebowski.di.module.RoomModule
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
            RoomModule::class,
            ViewModule::class,
            ViewModelModule::class
        ]
)
interface AppComponent: AndroidInjector<LebowskiApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder
        fun build(): AppComponent
    }
}