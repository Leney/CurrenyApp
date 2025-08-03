package com.crypto.currencyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currencies")
    suspend fun clearAll()

    @Query("SELECT * FROM currencies WHERE listSource = :source ORDER BY name ASC")
    fun getCurrenciesBySource(source: String): Flow<List<CurrencyEntity>>

    // --- 使用这个新的、更健壮的查询 ---
    @Query(
        """
        SELECT * FROM currencies
        WHERE id IN (
            SELECT id FROM currencies
            WHERE listSource = 'A' AND (',' || features || ',' LIKE '%,purchase,%')
            INTERSECT
            SELECT id FROM currencies
            WHERE listSource = 'B' AND (',' || features || ',' LIKE '%,purchase,%')
        )
        AND listSource = 'A' --
        ORDER BY name ASC
    """
    )
    fun getIntersectionPurchasable(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies")
    suspend fun getAllItemsForDebug(): List<CurrencyEntity>
}