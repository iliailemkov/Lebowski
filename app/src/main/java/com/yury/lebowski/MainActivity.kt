package com.yury.lebowski

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.settings.SettingsFragment
import com.yury.lebowski.ui.statistics.StatisticsFragment


class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
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
                navigateTo(StatisticsFragment.newInstance(), "NavigateStatistics")
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
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(transaction)
                .commit()
    }

    override fun navigateBack() {
        supportFragmentManager.popBackStack()
    }

}
