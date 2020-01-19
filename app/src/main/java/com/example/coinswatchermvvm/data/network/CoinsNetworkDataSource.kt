package com.example.coinswatchermvvm.data.network

import androidx.lifecycle.LiveData
import com.example.coinswatchermvvm.data.response.CoinResponse

interface CoinsNetworkDataSource {
    val downloadedCoins: LiveData<List<CoinResponse>>

    suspend fun fetchCoins()

}