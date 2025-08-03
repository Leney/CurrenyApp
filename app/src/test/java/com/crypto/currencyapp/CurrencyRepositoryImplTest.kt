package com.crypto.currencyapp

import app.cash.turbine.test
import com.crypto.currencyapp.data.local.CurrencyDao
import com.crypto.currencyapp.data.local.CurrencyEntity
import com.crypto.currencyapp.data.repository.CurrencyRepositoryImpl
import com.crypto.currencyapp.domain.ListType
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CurrencyRepositoryImplTest {

    private lateinit var dao: CurrencyDao
    private lateinit var repository: CurrencyRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = CurrencyRepositoryImpl(dao)
    }

    @Test
    fun `getCurrencies with LIST_A should call getCurrenciesBySource with A`() = runBlocking {
        val fakeEntities = listOf(
            CurrencyEntity("BTC", "Bitcoin", "BTC", listOf("purchase"), "A"),
            CurrencyEntity("ETH", "Ethereum", "ETH", listOf("purchase"), "A")
        )
        every { dao.getCurrenciesBySource("A") } returns flowOf(fakeEntities)

        val resultFlow = repository.getCurrencies(ListType.LIST_A)

        resultFlow.test {
            val currencyInfoList = awaitItem()
            assertEquals(2, currencyInfoList.size)
            assertEquals("BTC", currencyInfoList[0].id)
            assertEquals("Bitcoin", currencyInfoList[0].name)
            assertEquals("ETH", currencyInfoList[1].id)
            awaitComplete() // 确认Flow已完成
        }

        coVerify { dao.getCurrenciesBySource("A") }
    }

    @Test
    fun `getCurrencies with PURCHASABLE_BOTH should call getIntersectionPurchasable`() = runBlocking {
        val fakeEntities = listOf(CurrencyEntity("BTC", "Bitcoin", "BTC", listOf("purchase"), "A"))
        every { dao.getIntersectionPurchasable() } returns flowOf(fakeEntities)
        val resultFlow = repository.getCurrencies(ListType.PURCHASABLE_BOTH)
        resultFlow.test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("BTC", result[0].id)
            awaitComplete()
        }

        coVerify { dao.getIntersectionPurchasable() }
    }

    @Test
    fun `insertAllData should call dao's insertAll twice`() = runBlocking {
        repository.insertAllData()
        coVerify(exactly = 2) { dao.insertAll(any()) }
    }

    @Test
    fun `clearAllData should call dao's clearAll`() = runBlocking {
        repository.clearAllData()
        coVerify(exactly = 1) { dao.clearAll() }
    }
}