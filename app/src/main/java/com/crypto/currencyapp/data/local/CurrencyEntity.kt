package com.crypto.currencyapp.data.local

import androidx.room.Entity
import com.crypto.currencyapp.data.model.CurrencyInfo

@Entity(tableName = "currencies", primaryKeys = ["id","listSource"])
data class CurrencyEntity(
//    @PrimaryKey val id: String,
    val id: String,
    val name: String,
    val symbol: String,
    val features: List<String>,
    val listSource: String // "A" or "B"
)

fun CurrencyEntity.toCurrencyInfo(): CurrencyInfo {
    return CurrencyInfo(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        features = this.features
    )
}
