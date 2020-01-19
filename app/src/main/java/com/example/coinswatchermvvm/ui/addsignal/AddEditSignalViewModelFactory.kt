package com.example.coinswatchermvvm.ui.addsignal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinswatchermvvm.data.repository.SignalsRepository


class AddEditSignalViewModelFactory(
    private val symbol: String,
    private val signalsRepository: SignalsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddEditSignalViewModel(signalsRepository, symbol) as T
    }
}