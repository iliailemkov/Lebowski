package com.yury.lebowski

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.facebook.stetho.Stetho
import com.yury.lebowski.navigation.NavigationDetailContainer
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.service.PeriodicOperationWorker
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.settings.SettingsFragment
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        WorkManager.getInstance().getStatusesByTag(PeriodicOperationWorker.TAG).observeForever {
            for (work in it!!) {
                if (!work.state.isFinished) {
                    return@observeForever
                }
            }
            val periodicWorkRequest = PeriodicWorkRequest
                    .Builder(PeriodicOperationWorker::class.java, 15, TimeUnit.MINUTES)
                    .addTag(PeriodicOperationWorker.TAG)
                    .build()
            WorkManager.getInstance().enqueue(periodicWorkRequest)

        }
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HomeFragment.newInstance())
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.settings_item -> {
                navigateTo(SettingsFragment.newInstance(), "NavigateSettings")
                return false
            }
            R.id.statistics_item -> {
                //navigateTo(StatisticsFragment.newInstance(), "NavigateStatistics")
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initToolbar(title: Int, elevation: Float) {
        supportActionBar?.setTitle(title)
        supportActionBar?.elevation = elevation
    }

    override fun navigateTo(fragment: Fragment, transaction: String?) {
        if(isWindowWithDetail()) {
            fragment::class.java.declaredAnnotations.forEach {
                if(it is NavigatorMainContainer) {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.main_container, fragment)
                            .addToBackStack(transaction)
                            .commit()
                } else if(it is NavigationDetailContainer) {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.detail_container, fragment)
                            .disallowAddToBackStack()
                            .commit()
                }
            }
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(transaction)
                    .commit()
        }
    }

    override fun navigateBack() {
        supportFragmentManager.popBackStack()
    }

    override fun isWindowWithDetail() : Boolean {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels < displayMetrics.widthPixels && displayMetrics.widthPixels >= 900f
    }

}
