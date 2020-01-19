package com.example.coinswatchermvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.coinswatchermvvm.data.db.CoinsDao
import com.example.coinswatchermvvm.data.db.SignalEntry
import com.example.coinswatchermvvm.data.db.SignalsDao
import com.example.coinswatchermvvm.data.network.CoinsNetworkDataSource
import com.example.coinswatchermvvm.data.response.CoinResponse
import com.example.coinswatchermvvm.utils.CoinsPreferences
import com.example.coinswatchermvvm.utils.SignalChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//const val MIN_REQUEST_PERIOD = 1000*60*15 //1000 mills * 60 secs * 15 minutes

class CoinsRepositoryImpl(
    private val coinsDao: CoinsDao,
    private val coinsNetworkDataSource: CoinsNetworkDataSource,
    private val coinsPreferences: CoinsPreferences,
    private val signalChecker: SignalChecker
    ) : CoinsRepository {

    private val LOG_TAG = "CoinsRepositoryImpl_debugLog_"

//    var lastCoinsRequest: Long = 0


    init {
        coinsNetworkDataSource.apply {
            downloadedCoins.observeForever { newCoins ->
                persistFetchedCoins(newCoins)
            }
        }
    }

    private suspend fun fetchCoins() {
        coinsNetworkDataSource.fetchCoins()
    }

    override suspend fun getCoinById(id: String): LiveData<CoinResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext coinsDao.getCoinById(id)
        }
    }

    override suspend fun getCoinByRank(rank: Int): LiveData<CoinResponse> {
        return withContext(Dispatchers.IO) {
            return@withContext coinsDao.getCoinByRank(rank)
        }
    }

    override suspend fun getCoinsList(): LiveData<List<CoinResponse>> {

        return withContext(Dispatchers.IO) {
            initDBData()
            return@withContext coinsDao.getCoins()
        }
    }

    private suspend fun initDBData() {
        Log.d(LOG_TAG,"Current MinimalRequestPeriod: ${coinsPreferences.getMinimalRequestPeriod()}")
        if(coinsPreferences.getTimeOfTheLastRequest() == 0L || (coinsPreferences.getTimeOfTheLastRequest()
                    + coinsPreferences.getMinimalRequestPeriod() < System.currentTimeMillis())) {
            Log.d(LOG_TAG,"Need request Data From Server, current data expired or absent")
            fetchCoins()
        } else {
            Log.d(LOG_TAG,"Do not need request Data From Server, current data not expired yet")
            Log.d(LOG_TAG,"Last request was ${(System.currentTimeMillis() - coinsPreferences.getTimeOfTheLastRequest())/1000} seconds ago")
            Log.d(LOG_TAG,"CURRENT RECORDS IN DB: ${coinsDao.getCountRecords()}")
        }
    }

//    private suspend fun initDBData() {
//        if(lastCoinsRequest == 0L || (lastCoinsRequest + MIN_REQUEST_PERIOD < System.currentTimeMillis())) {
//            Log.d(LOG_TAG,"Need request Data From Server, current data expired or absent")
//            fetchCoins()
//        } else {
//            Log.d(LOG_TAG,"Do not need request Data From Server, current data not expired yet")
//            Log.d(LOG_TAG,"CURRENT RECORDS IN DB: ${coinsDao.getCountRecords()}")
//        }
//    }

    private fun persistFetchedCoins(list: List<CoinResponse>) {
        GlobalScope.launch(Dispatchers.IO) {

            coinsDao.insertList(list)

//            lastCoinsRequest = System.currentTimeMillis()
            coinsPreferences.setTimeOfTheLastRequest(System.currentTimeMillis())

//            checkSignals(list)
            signalChecker.checkSignals(list)
        }
    }

//    fun checkSignals(list: List<CoinResponse>) {
//        Log.d(LOG_TAG,"checkSignals +++++++++++++++++")
//        list.forEach{coin ->
//            Log.d(LOG_TAG,"checkSignals COIN: ${coin.symbol}")
//        }
//    }

//    private fun checkSignals(list: List<CoinResponse>) {
//        Log.d(LOG_TAG,"checkSignals +++++++++++++++++")
//    }

}