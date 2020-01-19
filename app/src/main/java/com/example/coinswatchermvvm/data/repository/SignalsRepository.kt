package com.example.coinswatchermvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.coinswatchermvvm.data.db.SignalEntry

interface SignalsRepository {

    suspend fun insertList(signalEntryList: List<SignalEntry>)


    suspend fun upsert(signal: SignalEntry)


    suspend fun getSignals(): LiveData<List<SignalEntry>>


    suspend fun deleteAllSignals()


    suspend fun deleteSignal(symbol: String)


    suspend fun getCountRecords(): Int



    suspend fun getSignalBySymbol(symbol: String): LiveData<SignalEntry>

    suspend fun getSignalBySymbol2(symbol: String): SignalEntry
}