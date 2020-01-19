package com.example.coinswatchermvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.example.coinswatchermvvm.data.db.SignalsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignalsRepositoryImpl(
    private val signalsDao: SignalsDao
) : SignalsRepository {

    private val LOG_TAG = "SignalsRepositoryImpl_debugLog_"

    override suspend fun insertList(signalEntryList: List<SignalEntry>) {
        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.insertList(signalEntryList)
        }
    }

    override suspend fun upsert(signal: SignalEntry) {
        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.upsert(signal)
        }
    }

    override suspend fun getSignals(): LiveData<List<SignalEntry>> {

        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.getSignals()
        }
    }

    override suspend fun deleteAllSignals() {
        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.deleteAllSignals()
        }
    }

    override suspend fun deleteSignal(symbol: String) {
        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.deleteSignal(symbol)
        }
    }

    override suspend fun getCountRecords(): Int {
        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.getCountRecords()
        }
    }

    override suspend fun getSignalBySymbol(symbol: String): LiveData<SignalEntry> {
        Log.d(LOG_TAG,"getSignalBySymbol symbol: $symbol")
        return withContext(Dispatchers.IO) {
            return@withContext signalsDao.getSignalBySymbol(symbol)
        }
    }

    override suspend fun getSignalBySymbol2(symbol: String): SignalEntry {
        Log.d(LOG_TAG,"getSignalBySymbol symbol: $symbol")
        return withContext(Dispatchers.IO) {
            val signalEntry: SignalEntry = signalsDao.getSignalBySymbol2(symbol)
            if(signalEntry != null) {
                Log.d(LOG_TAG,"getSignalBySymbol signalEntry: ${signalEntry.symbol}")
            } else {
                Log.d(LOG_TAG,"getSignalBySymbol signalEntry_IS_NULL?: ${signalEntry} symbol: $symbol")
            }
//            Log.d(LOG_TAG,"getSignalBySymbol signalEntry: ${signalEntry.symbol}")
            return@withContext signalEntry // signalsDao.getSignalBySymbol(symbol)
        }
    }
}