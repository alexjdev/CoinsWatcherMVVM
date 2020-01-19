package com.example.coinswatchermvvm.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinswatchermvvm.data.repository.CoinsRepository
import com.example.coinswatchermvvm.utils.lazyDeferred

class CoinDetailViewModel(
    private  val coinsRepository: CoinsRepository,
    private  val rank: Int

) : ViewModel() {

    private val LOG_TAG = "CoinDetailViewModel_debugLog_"

    val coinResponse by lazyDeferred{
        coinsRepository.getCoinByRank(rank)
    }

    public fun onBtnTestClicked() {
        Log.d(LOG_TAG,"onBtnTestClicked")
    }

    fun onAddSignalBtnClicked() {
        Log.d(LOG_TAG,"onAddSignalBtnClicked")
        //addSignal()
    }

//    fun addSignal() {
//        val actionDetail = MainCoinsListFragmentDirections.actionDetail(coinResponse.symbol)
//        Navigation.findNavController(view).navigate(actionDetail)
//    }

}
