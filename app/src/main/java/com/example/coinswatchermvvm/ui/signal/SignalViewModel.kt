package com.example.coinswatchermvvm.ui.signal

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinswatchermvvm.data.repository.SignalsRepository
import com.example.coinswatchermvvm.utils.lazyDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class SignalViewModel(
 //   private  val signalsRepository: SignalsRepository
) : ViewModel() {
    // TODO: Implement the ViewModel


    private val LOG_TAG = "SignalViewModel_debugLog_"

    fun deleteSignal(symbol: String?) {
        Log.d(LOG_TAG,"CLICKED BTN DELETE SIGNAL symbol: $symbol")
        if(symbol != null) {
//            onRemoveSignal(symbol)
        }
    }

//    private fun onRemoveSignal(symbol: String) {
//        Log.d(LOG_TAG,"onRemoveSignal_symbol: $symbol")
//        runBlocking {
//            async { signalsRepository.deleteSignal(symbol) }.await() //TODO:CHECK IT
//        }
//        //getCountSignals()// remove later if works correct
//    }

}
