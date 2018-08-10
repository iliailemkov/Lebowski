package com.yury.lebowski

import com.squareup.leakcanary.LeakCanary
import com.yury.lebowski.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class LebowskiApplication() : DaggerApplication() {
    companion object {
        lateinit var instance: LebowskiApplication
            private set
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .create(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this)
    }
}