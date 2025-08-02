package com.crypto.currencyapp.domain

import com.crypto.currencyapp.data.model.CurrencyInfo
import com.crypto.currencyapp.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(listType: ListType): Flow<List<CurrencyInfo>> {
        return repository.getCurrencies(listType)
    }
}