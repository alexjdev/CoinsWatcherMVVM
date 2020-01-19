package com.example.coinswatchermvvm.ui.signalslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinswatchermvvm.data.repository.SignalsRepository


class SignalsListViewModelFactory(
    private val signalsRepository: SignalsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignalsListViewModel(signalsRepository) as T
    }
}