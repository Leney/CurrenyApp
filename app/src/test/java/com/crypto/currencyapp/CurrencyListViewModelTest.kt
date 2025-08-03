package com.crypto.currencyapp

import app.cash.turbine.test
import com.crypto.currencyapp.data.model.CurrencyInfo
import com.crypto.currencyapp.domain.GetCurrenciesUseCase
import com.crypto.currencyapp.domain.ListType
import com.crypto.currencyapp.ui.list.CurrencyListViewModel
import com.crypto.currencyapp.util.UiState
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CurrencyListViewModelTest {
    private lateinit var getCurrenciesUseCase: GetCurrenciesUseCase
    private lateinit var viewModel: CurrencyListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getCurrenciesUseCase = mockk()
        viewModel = CurrencyListViewModel(getCurrenciesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        assertEquals(UiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `loadCurrencies should emit Loading then Success when use case returns data`() = runTest {
        // Given
        val fakeData = listOf(CurrencyInfo("BTC", "Bitcoin", "BTC", emptyList()))
        every { getCurrenciesUseCase(any()) } returns flowOf(fakeData)

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            viewModel.loadCurrencies(ListType.LIST_A)
            val result = awaitItem()
            assertTrue(result is UiState.Success)
            assertEquals(fakeData, (result as UiState.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadCurrencies should emit Empty when use case returns empty list`() = runTest {
        // Given
        every { getCurrenciesUseCase(any()) } returns flowOf(emptyList())

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            viewModel.loadCurrencies(ListType.LIST_B)
            assertEquals(UiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadCurrencies should emit Empty when use case throws exception`() = runTest {
        val exception = RuntimeException("Network Error")
        every { getCurrenciesUseCase(any()) } returns flow { throw exception }

        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            viewModel.loadCurrencies(ListType.LIST_A)
            assertEquals(UiState.Empty, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}