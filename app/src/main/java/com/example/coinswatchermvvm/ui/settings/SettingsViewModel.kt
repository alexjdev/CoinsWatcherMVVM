package com.example.coinswatchermvvm.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinswatchermvvm.utils.CoinsPreferences
//import com.example.coinswatchermvvm.utils.lazyDeferred


class SettingsViewModel(
    private  val coinsPreferences: CoinsPreferences

) : ViewModel() {

    private val LOG_TAG = "SettingsViewModel_debugLog_"

//    val coinsPreferences by lazyDeferred{
////        coinsRepository.getCoinByRank(rank)
//    }

    fun onBtnRadio0Clicked() {
        Log.d(LOG_TAG,"onBtnRadio0Clicked")
        saveMinRequestInterval(1000*60*1)
    }

    fun onBtnRadio1Clicked() {
        Log.d(LOG_TAG,"onBtnRadio1Clicked")
        saveMinRequestInterval(1000*60*15)
    }

    fun onBtnRadio2Clicked() {
        saveMinRequestInterval(1000*60*30)
    }

    fun onBtnRadio3Clicked() {
        saveMinRequestInterval(1000*60*60)
    }

    private fun saveMinRequestInterval(interval: Int) {
        coinsPreferences.setMinimalRequesPeriod(interval)
    }

    fun getCurrentMinRequestInterval(): Int {
        return  coinsPreferences.getMinimalRequestPeriod()
    }
}