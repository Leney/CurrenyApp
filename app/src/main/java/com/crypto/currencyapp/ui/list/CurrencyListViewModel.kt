package com.crypto.currencyapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.currencyapp.data.model.CurrencyInfo
import com.crypto.currencyapp.domain.GetCurrenciesUseCase
import com.crypto.currencyapp.domain.ListType
import com.crypto.currencyapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CurrencyInfo>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CurrencyInfo>>> = _uiState.asStateFlow()

    private var currentJob: Job? = null

    // Activity 将会调用这个方法来加载或切换列表
    fun loadCurrencies(listType: ListType) {
        currentJob?.cancel() // 取消上一次的加载任务
        currentJob = viewModelScope.launch {
            _uiState.value = UiState.Loading // 开始加载，显示加载状态
            getCurrenciesUseCase(listType)
                .map { currencies ->
                    if (currencies.isEmpty()) UiState.Empty else UiState.Success(currencies)
                }
                .catch {
                    // 如果有异常，可以发射一个错误状态
                    // emit(UiState.Error(it.message ?: "Unknown error"))
                    _uiState.value = UiState.Empty // 简单处理，同样显示为空
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }
}