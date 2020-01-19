package com.example.coinswatchermvvm.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinswatchermvvm.utils.CoinsPreferences


class SettingsViewModelFactory(

    private val coinsPreferences: CoinsPreferences
) : ViewModelProvider.NewInstanceFactory() {

    private val LOG_TAG = "SettingsViewModelFactory_debugLog_"

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.d(LOG_TAG,"ViewModel created")
        return SettingsViewModel(coinsPreferences) as T
    }
}