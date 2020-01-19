package com.example.coinswatchermvvm.utils

import android.content.Context
import com.example.coinswatchermvvm.utils.exceptions.IncorrectPreferencesParamException

const val PREFERENCES = "CoinsWatcherPrefsMVVM"

const val MINIMAL_REQUEST_PERIOD = "MinimalRequesPeriod"

const val TIME_OF_THE_LAST_REUEST = "TimeOfTheLastRequest"

//const val MIN_REQUEST_PERIOD = 1000*60*15 //1000 mills * 60 secs * 15 minutes
const val MIN_REQUEST_PERIOD = 1000*60*1 //1000 mills * 60 secs * 15 minutes !!!!! 1 MINUTE FOR TEST ONLY !!!!!!!!!!!!!!!!!!!!!!

class CoinsPreferences(context: Context) {

    val preference = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)


    fun getMinimalRequestPeriod() : Int {
        return preference.getInt(MINIMAL_REQUEST_PERIOD, MIN_REQUEST_PERIOD)
    }

    fun setMinimalRequesPeriod(minutes: Int) {
        if(minutes < MIN_REQUEST_PERIOD ) throw IncorrectPreferencesParamException()
        val editor = preference.edit()
        editor.putInt(MINIMAL_REQUEST_PERIOD, minutes)
        editor.apply()
    }

    fun getTimeOfTheLastRequest() : Long {
        return preference.getLong(TIME_OF_THE_LAST_REUEST, 0)
    }

    fun setTimeOfTheLastRequest(minutes: Long) {
        val editor = preference.edit()
        editor.putLong(TIME_OF_THE_LAST_REUEST, minutes)
        editor.apply()
    }

}