package com.yury.lebowski.data.repository

import android.content.SharedPreferences
import com.yury.lebowski.data.local.models.Account
import javax.inject.Inject

class SharedPrefRepository @Inject constructor(var shared : SharedPreferences) {

    companion object {
        const val DEFAULT_BALANCE = "DefaultBalance"
        const val ONLY_OUTCOME = "OnlyOutcome"
        const val SHOW_LEGEND = "ShowLegend"
        const val USER_NAME = "UserName"
        const val USER_EMAIL = "UserEmail"
    }

    fun setUserName(name : String) {
        shared.edit().putString(USER_NAME, name).apply()
    }

    fun getUserName() : String {
        return shared.getString(USER_NAME, "")
    }

    fun setUserEmail(email : String) {
        shared.edit().putString(USER_EMAIL, email).apply()
    }

    fun getUserEmail() : String {
        return shared.getString(USER_EMAIL, "")
    }

    fun setDefaultBalanace(account : Account) {
        shared.edit().putLong(DEFAULT_BALANCE, account.id!!).apply()
    }

    fun getDefaultBalanace() : Long {
        return shared.getLong(DEFAULT_BALANCE, 0)
    }

    fun setOnlyOutcomes(value : Boolean) {
        shared.edit().putBoolean(ONLY_OUTCOME, value).apply()
    }

    fun getOnlyOutcomes() : Boolean {
        return shared.getBoolean(ONLY_OUTCOME, false)
    }

    fun setShowlegend(value : Boolean) {
        shared.edit().putBoolean(SHOW_LEGEND, value).apply()
    }

    fun getShowlegend() : Boolean {
        return shared.getBoolean(SHOW_LEGEND, true)
    }
}