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
            // 1. 消费掉初始的 Loading 状态
            assertEquals(UiState.Loading, awaitItem())

            // 2. 触发加载
            viewModel.loadCurrencies(ListType.LIST_A)

            // 3. 因为是 Unconfined dispatcher，`_uiState.value = UiState.Loading`
            //    和后面的 collect 会立即执行。
            //    StateFlow 会合并相同的连续值，所以我们只会收到最终的 Success 状态。
            //    所以我们不需要再断言中间的 Loading 状态了。
            val result = awaitItem()
            assertTrue(result is UiState.Success)
            assertEquals(fakeData, (result as UiState.Success).data)

            // 4. 为确保没有其他意外的发射，取消监听。
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadCurrencies should emit Empty when use case returns empty list`() = runTest {
        // Given
        every { getCurrenciesUseCase(any()) } returns flowOf(emptyList())

        viewModel.uiState.test {
            // 1. 消费掉初始的 Loading 状态
            assertEquals(UiState.Loading, awaitItem())

            // 2. 触发加载
            viewModel.loadCurrencies(ListType.LIST_B)

            // 3. 直接断言最终的 Empty 状态
            assertEquals(UiState.Empty, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadCurrencies should emit Empty when use case throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network Error")
        every { getCurrenciesUseCase(any()) } returns flow { throw exception }

        viewModel.uiState.test {
            // 1. 消费掉初始的 Loading 状态
            assertEquals(UiState.Loading, awaitItem())

            // 2. 触发加载
            viewModel.loadCurrencies(ListType.LIST_A)

            // 3. 直接断言最终的 Empty 状态 (来自catch块)
            assertEquals(UiState.Empty, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}