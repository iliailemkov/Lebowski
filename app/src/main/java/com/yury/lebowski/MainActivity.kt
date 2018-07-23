package com.yury.lebowski

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.yury.lebowski.ui.home.HomeFragment
import com.yury.lebowski.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity(), SettingsFragment.SettingFragmentListener {
    override fun onAboutClicked() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.settings_item) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, HomeFragment.newInstance())
                    .commit()
        }
    }

}
