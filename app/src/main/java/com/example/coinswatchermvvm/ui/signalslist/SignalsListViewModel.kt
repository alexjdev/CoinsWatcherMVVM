package com.example.coinswatchermvvm.ui.signalslist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinswatchermvvm.data.repository.SignalsRepository
import com.example.coinswatchermvvm.utils.lazyDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class SignalsListViewModel(
    private val signalsRepository: SignalsRepository
) : ViewModel() {

    private val LOG_TAG = "SignalsListViewModel_debugLog_"

    val signalsList by lazyDeferred {
        signalsRepository.getSignals()
    }

    fun deleteAllSignals() {
        Log.d(LOG_TAG,"deleteAllSignals ")
        runBlocking {
            async { signalsRepository.deleteAllSignals() }.await() //TODO:CHECK IT
        }
        getSignalsCount()
    }

//    fun getSignalsCount() {
//        Log.d(LOG_TAG,"getSignalsCount ")
//        runBlocking {
//            async { Log.d(LOG_TAG,"getSignalsCount ${signalsRepository.getCountRecords() }.await()}") }.await() //TODO:CHECK IT
//        }
//    }

    fun getSignalsCount(): Int {
        Log.d(LOG_TAG,"getSignalsCount ")
        var ret: Int = 0
        runBlocking {
            async {
                ret = signalsRepository.getCountRecords()
            }.await() //TODO:CHECK IT
        }
        Log.d(LOG_TAG,"getSignalsCount: $ret ")
        return ret
    }
}