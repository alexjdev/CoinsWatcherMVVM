package com.example.coinswatchermvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.coinswatchermvvm.data.response.CoinResponse

interface CoinsRepository {
    suspend fun getCoinsList(): LiveData<List<CoinResponse>>


    suspend fun getCoinByRank(rank: Int): LiveData<CoinResponse>


    suspend fun getCoinById(id: String): LiveData<CoinResponse>
}