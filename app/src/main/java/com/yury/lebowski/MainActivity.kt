package com.yury.lebowski

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.yury.lebowski.models.OperationType
import com.yury.lebowski.ui.about.AboutFragment
import com.yury.lebowski.ui.add_operation.AddOperationFragment
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentListener, SettingsFragment.SettingFragmentListener {

    private fun navigateToFragment(fragmentInstance: () -> Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragmentInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, HomeFragment.newInstance())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.settings_item) {
            navigateToFragment { SettingsFragment.newInstance() }
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAboutClicked() {
        navigateToFragment { AboutFragment.newInstance() }
    }

    override fun onAddOperationClicked(operationType: OperationType) {
        navigateToFragment { AddOperationFragment.newInstance(operationType)}
    }
}
