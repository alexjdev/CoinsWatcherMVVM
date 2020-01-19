package com.example.coinswatchermvvm.ui.maincoinslist

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinswatchermvvm.data.network.CoinsApiService
import com.example.coinswatchermvvm.data.repository.CoinsRepository
import com.example.coinswatchermvvm.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainCoinsListViewModel(
    private val coinsRepository: CoinsRepository
) : ViewModel() {

    private val LOG_TAG = "MainCoinsListViewModel_debugLog_"

    val coinsList by lazyDeferred {
        coinsRepository.getCoinsList()
    }
}