package com.example.coinswatchermvvm.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coinswatchermvvm.data.response.CoinResponse
import com.example.coinswatchermvvm.utils.exceptions.NoInternetConnectionException

class CoinsNetworkDataSourceImpl(
    private val coinsApiService: CoinsApiService
) : CoinsNetworkDataSource {

    private val _downloadedCoins = MutableLiveData<List<CoinResponse>>()
    override val downloadedCoins: LiveData<List<CoinResponse>>
        get() = _downloadedCoins


    private val LOG_TAG = "CoinsNetworkDataSourceImpl_debugLog_"

    override suspend fun fetchCoins() {
        try {
            val fetchedCoins = coinsApiService
                .getCoinsList()
                .await()
            _downloadedCoins.postValue(fetchedCoins)

            Log.d(LOG_TAG,"getDataFromServer_______")
            val length = fetchedCoins.size
            for (i in 0 until length) {
                Log.d(LOG_TAG,"GOT COININFO: ${fetchedCoins!!.get(i).toString()}")
            }
            Log.d(LOG_TAG,"getDataFromServer_FINISH________")

        }
        catch (e: NoInternetConnectionException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

}