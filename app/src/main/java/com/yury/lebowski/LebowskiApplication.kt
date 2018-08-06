package com.yury.lebowski

<<<<<<< HEAD
import android.app.Application
import com.squareup.leakcanary.LeakCanary
=======
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.yury.lebowski.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
>>>>>>> develop


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
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this)
    }
}