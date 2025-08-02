package com.crypto.currencyapp.ui.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currencyapp.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    fun insertData() {
        viewModelScope.launch {
            repository.insertAllData()
        }
    }

    fun clearData() {
        viewModelScope.launch {
            repository.clearAllData()
        }
    }
}