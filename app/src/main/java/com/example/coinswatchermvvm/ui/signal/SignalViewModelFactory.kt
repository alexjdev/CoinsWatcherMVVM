package com.example.coinswatchermvvm.ui.signal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinswatchermvvm.data.repository.SignalsRepository

class SignalViewModelFactory(
//    private val signalsRepository: SignalsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignalViewModel(/*signalsRepository*/) as T
    }
}