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
        // 在每个测试运行前，创建一个假的DAO
        dao = mockk(relaxed = true) // relaxed = true 让所有未明确mock的方法返回默认值
        repository = CurrencyRepositoryImpl(dao)
    }

    @Test
    fun `getCurrencies with LIST_A should call getCurrenciesBySource with A`() = runBlocking {
        // Given: 准备一个假的Entity列表
        val fakeEntities = listOf(
            CurrencyEntity("BTC", "Bitcoin", "BTC", listOf("purchase"), "A"),
            CurrencyEntity("ETH", "Ethereum", "ETH", listOf("purchase"), "A")
        )
        // 当 dao.getCurrenciesBySource("A") 被调用时，返回我们准备的假数据流
        every { dao.getCurrenciesBySource("A") } returns flowOf(fakeEntities)

        // When: 调用仓库的方法
        val resultFlow = repository.getCurrencies(ListType.LIST_A)

        // Then: 验证结果
        resultFlow.test {
            val currencyInfoList = awaitItem()
            assertEquals(2, currencyInfoList.size)
            assertEquals("BTC", currencyInfoList[0].id)
            assertEquals("Bitcoin", currencyInfoList[0].name)
            assertEquals("ETH", currencyInfoList[1].id)
            awaitComplete() // 确认Flow已完成
        }

        // 验证 dao.getCurrenciesBySource("A") 是否真的被调用了
        coVerify { dao.getCurrenciesBySource("A") }
    }

    @Test
    fun `getCurrencies with PURCHASABLE_BOTH should call getIntersectionPurchasable`() = runBlocking {
        // Given
        val fakeEntities = listOf(CurrencyEntity("BTC", "Bitcoin", "BTC", listOf("purchase"), "A"))
        every { dao.getIntersectionPurchasable() } returns flowOf(fakeEntities)

        // When
        val resultFlow = repository.getCurrencies(ListType.PURCHASABLE_BOTH)

        // Then
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
        // When
        repository.insertAllData()

        // Then: 验证dao.insertAll被调用了两次
        coVerify(exactly = 2) { dao.insertAll(any()) }
    }

    @Test
    fun `clearAllData should call dao's clearAll`() = runBlocking {
        // When
        repository.clearAllData()

        // Then
        coVerify(exactly = 1) { dao.clearAll() }
    }
}