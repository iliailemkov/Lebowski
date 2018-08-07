package com.yury.lebowski.ui.settings

import androidx.lifecycle.ViewModel
import com.yury.lebowski.data.repository.SharedPrefRepository
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
        private val sprefRepository: SharedPrefRepository
) : ViewModel() {

    fun setSummaryBoolean(value: Boolean) {
        sprefRepository.setOnlyOutcomes(value)
    }

    fun getSummaryBoolean(): Boolean {
        return sprefRepository.getOnlyOutcomes()
    }

    fun setShowLegend(value: Boolean) {
        sprefRepository.setShowlegend(value)
    }

    fun getShowLegend(): Boolean {
        return sprefRepository.getShowlegend()
    }
}