package com.example.coinswatchermvvm.data.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SignalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun insertList(signalEntryList: List<SignalEntry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(signal: SignalEntry)

    @Query("select * from signals")
    fun getSignals(): LiveData<List<SignalEntry>>

    @Query("delete from signals")
    fun deleteAllSignals()

    @Query("delete from signals where symbol LIKE :symbol")
    fun deleteSignal(symbol: String)

    @Query("select count(symbol) from signals")
    fun getCountRecords(): Int


    @Query("select * from signals where symbol LIKE :symbol LIMIT 1") //LIMIT 1 - unnecessary
    fun getSignalBySymbol(symbol: String): LiveData<SignalEntry>

    @Query("select * from signals where symbol LIKE :symbol") //LIMIT 1 - unnecessary
    fun getSignalBySymbol2(symbol: String): SignalEntry

}