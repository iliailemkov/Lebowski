package com.yury.lebowski

import android.os.Bundle
 import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.facebook.stetho.Stetho
import com.yury.lebowski.data.local.models.enums.OperationType
import com.yury.lebowski.navigation.NavigatiorDetailContainer
import com.yury.lebowski.navigation.Navigator
import com.yury.lebowski.navigation.NavigatorMainContainer
import com.yury.lebowski.service.PeriodicOperationWorker
import com.yury.lebowski.ui.add_account.AddAccountFragment
import com.yury.lebowski.ui.add_operation.AddOperationFragment
import com.yury.lebowski.ui.get_summary.GetSummaryFragment
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.settings.SettingsFragment
import kotlinx.android.synthetic.main.main_activity.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(),
        FragmentManager.OnBackStackChangedListener,
        Navigator {

    private val TOOLBAR_TITLE = "toolbar_title"

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
                .build())
        setSupportActionBar(toolbar)
        if(savedInstanceState != null) {
            supportActionBar?.title = savedInstanceState.getString(TOOLBAR_TITLE) ?: ""
        }
        initSpeedDial()
        supportFragmentManager.addOnBackStackChangedListener(this)

        if(supportFragmentManager.backStackEntryCount == 0) {
            toolbar.setTitle(R.string.app_name)
            toolbar.elevation = resources.getDimension(R.dimen.toolbar_elevation)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, HomeFragment.newInstance())
                    .commit()
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            speedDial.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        if(supportFragmentManager.backStackEntryCount > 0) {
            toolbar.menu.findItem(R.id.settings_item).isVisible = false
            toolbar.menu.findItem(R.id.statistics_item).isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                navigateBack()
                return false
            }
            R.id.settings_item -> {
                navigateTo(SettingsFragment.newInstance(), "NavigateSettings")
                return false
            }
            R.id.statistics_item -> {
                navigateTo(GetSummaryFragment.newInstance(), "NavigateGetSummaryFragment")
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TOOLBAR_TITLE, toolbar.title.toString())
    }

    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.menu.findItem(R.id.settings_item).isVisible = false
            toolbar.menu.findItem(R.id.statistics_item).isVisible = false
            speedDial.visibility = View.GONE
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toolbar.setTitle(R.string.app_name)
            toolbar.elevation = resources.getDimension(R.dimen.toolbar_elevation)
            toolbar.menu.findItem(R.id.settings_item).isVisible = true
            toolbar.menu.findItem(R.id.statistics_item).isVisible = true
            speedDial.visibility = View.VISIBLE
        }
    }

    private fun initSpeedDial() {
        speedDial.inflate(R.menu.menu_speed_dial)
        speedDial.setOnActionSelectedListener { it ->
            when (it.id) {
                R.id.add_account -> navigateTo(AddAccountFragment.newInstance(), "NavigateToAddACcount")
                R.id.add_income -> navigateTo(AddOperationFragment.newInstance(OperationType.Income), "NaviagteAddIncome")
                R.id.add_expenditure -> navigateTo(AddOperationFragment.newInstance(OperationType.Expenditure), "NaviagteAddExpenditure")
            }
            false
        }
    }

    override fun initToolbar(title: Int, elevation: Int?, fragment: Fragment) {
        if (!isWindowWithDetail() || fragment::class.java.declaredAnnotations.any { it is NavigatorMainContainer }) {
            toolbar.setTitle(title)
        }
        if(elevation == null) {
            toolbar.elevation = 0f
        } else {
            toolbar.elevation = resources.getDimension(elevation)
        }
    }

    override fun navigateTo(fragment: Fragment, transaction: String?) {
        if (isWindowWithDetail()) {
            fragment::class.java.declaredAnnotations.forEach {
                if (it is NavigatorMainContainer) {
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                            .add(R.id.main_container, fragment)
                            .addToBackStack(transaction)
                            .commit()
                    return
                }
                if (it is NavigatiorDetailContainer) {
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)

                            .replace(R.id.detail_container, fragment)
                            .commit()
                    return
                }
                return
            }
        } else {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.main_container, fragment)
                    .addToBackStack(transaction)
                    .commit()
        }
    }

    override fun navigateBack() {
        supportFragmentManager.popBackStack()
    }

    override fun isWindowWithDetail(): Boolean {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels < displayMetrics.widthPixels && displayMetrics.widthPixels >= 900f
    }

}
