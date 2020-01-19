package com.example.coinswatchermvvm.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinswatchermvvm.data.repository.CoinsRepository

//class CoinDetailViewModelFactory {
//}

class CoinDetailViewModelFactory(
    private val rank: Int,
    private val coinsRepository: CoinsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoinDetailViewModel(coinsRepository, rank) as T
    }
}