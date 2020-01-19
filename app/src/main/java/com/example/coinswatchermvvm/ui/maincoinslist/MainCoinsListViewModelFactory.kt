package com.example.coinswatchermvvm.ui.maincoinslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinswatchermvvm.data.repository.CoinsRepository


class MainCoinsListViewModelFactory(
    private val coinsRepository: CoinsRepository
    ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainCoinsListViewModel(coinsRepository) as T
    }
}