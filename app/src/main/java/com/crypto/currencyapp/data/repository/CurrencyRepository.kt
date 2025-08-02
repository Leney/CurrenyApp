package com.crypto.currencyapp.data.repository

import com.crypto.currencyapp.data.model.CurrencyInfo
import com.crypto.currencyapp.domain.ListType
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencies(listType: ListType): Flow<List<CurrencyInfo>>
    suspend fun insertAllData()
    suspend fun clearAllData()
}