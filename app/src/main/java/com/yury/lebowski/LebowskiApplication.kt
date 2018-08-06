package com.yury.lebowski

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.squareup.leakcanary.LeakCanary
import com.yury.lebowski.util.data_binding.BindingComponent

class LebowskiApplication() : Application() {
    companion object {
        lateinit var instance: LebowskiApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        //DataBindingUtil.setDefaultComponent(BindingComponent())
    }
}