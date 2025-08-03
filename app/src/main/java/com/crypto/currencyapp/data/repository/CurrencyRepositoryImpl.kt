package com.crypto.currencyapp.data.repository

import android.util.Log
import com.crypto.currencyapp.data.local.CurrencyDao
import com.crypto.currencyapp.data.local.toCurrencyInfo
import com.crypto.currencyapp.data.model.CurrencyInfo
import com.crypto.currencyapp.data.stati.DataSource
import com.crypto.currencyapp.domain.ListType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val dao: CurrencyDao
) : CurrencyRepository {
    override fun getCurrencies(listType: ListType): Flow<List<CurrencyInfo>> {
        val flow = when (listType) {
            ListType.LIST_A -> dao.getCurrenciesBySource("A")
            ListType.LIST_B -> dao.getCurrenciesBySource("B")
            ListType.PURCHASABLE_BOTH -> dao.getIntersectionPurchasable()
        }
        return flow.map { entities -> entities.map { it.toCurrencyInfo() } }
    }

    override suspend fun insertAllData() {
        // 先清空，确保是干净的插入
        dao.clearAll()
//        Log.d("DEBUG_REPO", "Database cleared before insert.")

        val listA = DataSource.currencyListA
        val listB = DataSource.currencyListB

        dao.insertAll(listA)
        dao.insertAll(listB)
//        Log.d("DEBUG_REPO", "Attempted to insert ${listA.size} items from List A and ${listB.size} items from List B.")

//        val allItems = dao.getAllItemsForDebug()
//        Log.d("DEBUG_REPO", "Verification: Total items in DB after insert: ${allItems.size}")
//        allItems.take(5).forEach { // 打印前5条看看
//            Log.d("DEBUG_REPO", "Item in DB: ${it.id}, Source: ${it.listSource}, Features: ${it.features}")
//        }
    }

    override suspend fun clearAllData() {
        dao.clearAll()
    }
}