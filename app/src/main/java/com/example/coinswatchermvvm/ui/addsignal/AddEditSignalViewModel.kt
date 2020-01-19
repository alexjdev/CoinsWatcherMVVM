package com.example.coinswatchermvvm.ui.addsignal

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.example.coinswatchermvvm.data.repository.CoinsRepository
import com.example.coinswatchermvvm.data.repository.SignalsRepository
import com.example.coinswatchermvvm.utils.lazyDeferred
import kotlinx.android.synthetic.main.add_edit_signal_fragment.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AddEditSignalViewModel(
    private  val signalsRepository: SignalsRepository,
    private  val symbol: String

) : ViewModel() {

    private val LOG_TAG = "AddEditSignalViewModel_debugLog_"

    val signalEntry by lazyDeferred{
        signalsRepository.getSignalBySymbol(symbol)
    }

    public fun onSaveSignal(signalEntry: SignalEntry) {
        Log.d(LOG_TAG,"onSaveSignal_signalEntry: $signalEntry")
        runBlocking {
            async { signalsRepository.upsert(signalEntry) }.await() //TODO:CHECK IT
        }
//        signalsRepository.upsert(signalEntry)
        getCountSignals()// remove later if works correct
    }

    public fun onSaveSignal() {
        Log.d(LOG_TAG,"onSaveSignal")
        //signalsRepository.upsert(SignalEntry(symbol, editText_usd_take_profit ))
    }

    public fun onSaveSignal2(text: String) {
        Log.d(LOG_TAG,"onSaveSignal_text: $text")
//        val edittextUsdTakeProfit = editText_usd_take_profit
        //signalsRepository.upsert(SignalEntry(symbol, editText_usd_take_profit ))
    }

    fun getCountSignals() {
        runBlocking {
            async { Log.d(LOG_TAG,"onSaveSignal_getCountSignals: ${signalsRepository.getCountRecords()}")  }.await() //TODO:CHECK IT
        }
    }


    fun onRemoveSignal(symbol: String) {
        Log.d(LOG_TAG,"onRemoveSignal_signalEntry: $symbol")
        runBlocking {
            async { signalsRepository.deleteSignal(symbol) }.await() //TODO:CHECK IT
        }
        getCountSignals()// remove later if works correct
    }
}
