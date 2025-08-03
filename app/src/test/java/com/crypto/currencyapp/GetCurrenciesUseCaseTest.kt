package com.crypto.currencyapp

import com.crypto.currencyapp.data.model.CurrencyInfo
import com.crypto.currencyapp.data.repository.CurrencyRepository
import com.crypto.currencyapp.domain.GetCurrenciesUseCase
import com.crypto.currencyapp.domain.ListType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCurrenciesUseCaseTest {
    private lateinit var repository: CurrencyRepository
    private lateinit var useCase: GetCurrenciesUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetCurrenciesUseCase(repository)
    }

    @Test
    fun `invoke with any ListType should call repository's getCurrencies`(): Unit = runBlocking {
        val fakeList = listOf(CurrencyInfo("id", "name", "symbol", emptyList()))
        every { repository.getCurrencies(any()) } returns flowOf(fakeList)
        useCase(ListType.LIST_A)
        verify { repository.getCurrencies(ListType.LIST_A) }
    }
}