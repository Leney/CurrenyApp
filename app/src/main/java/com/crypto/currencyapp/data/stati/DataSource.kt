package com.crypto.currencyapp.data.stati

import com.crypto.currencyapp.data.local.CurrencyEntity

object DataSource {
    val currencyListA = listOf(
        CurrencyEntity("BTC", "Bitcoin", "BTC", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "A"),
        CurrencyEntity("ETH", "Ethereum", "ETH", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "A"),
        CurrencyEntity("XRP", "XRP", "XRP", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "A"),
        CurrencyEntity("BCH", "Bitcoin Cash", "BCH", listOf("exchange"), "A"),
        CurrencyEntity("LTC", "Litecoin", "LTC", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "A"),
        CurrencyEntity("EOS", "EOS", "EOS", listOf("purchase", "exchange", "transfer"), "A"),
        CurrencyEntity("BNB", "Binance Coin", "BNB", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "A"),
        CurrencyEntity("LINK", "Chainlink", "LINK", listOf("purchase", "exchange", "transfer", "withdraw", "deposit"), "A"),
        CurrencyEntity("NEO", "NEO", "NEO", listOf("purchase", "exchange", "transfer"), "A"),
        CurrencyEntity("ETC", "Ethereum Classic", "ETC", listOf("purchase", "exchange", "transfer"), "A"),
        CurrencyEntity("ONT", "Ontology", "ONT", listOf("purchase", "exchange", "transfer"), "A"),
        CurrencyEntity("CRO", "Crypto.com Chain", "CRO", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "A"),
        CurrencyEntity("CUC", "Cucumber", "CUC", listOf("exchange", "transfer", "deposit", "purchase", "withdraw"), "A"),
        CurrencyEntity("USDC", "USD Coin", "USDC", listOf("exchange"), "A")
    )

    val currencyListB = listOf(
        CurrencyEntity("BTC", "Bitcoin", "BTC", listOf("deposit", "purchase", "exchange"), "B"),

        CurrencyEntity("BAT", "Basic Attention Token", "BAT", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "B"),
        CurrencyEntity("QTUM", "Qtum", "QTUM", listOf("purchase", "exchange", "transfer"), "B"),
        CurrencyEntity("TUSD", "TrueUSD", "TUSD", listOf("exchange", "deposit", "purchase", "transfer", "withdraw"), "B"),
        CurrencyEntity("OMG", "OmiseGO", "OMG", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "B"),
        CurrencyEntity("PAX", "Paxos Standard", "PAX", listOf("deposit", "withdraw", "purchase", "exchange", "transfer"), "B"),
        CurrencyEntity("HOT", "Holo", "HOT", listOf("purchase", "transfer", "exchange", "withdraw", "deposit"), "B"),
        CurrencyEntity("WAVES", "Waves", "WAVES", listOf("purchase", "exchange", "transfer"), "B"),
        CurrencyEntity("ICX", "ICON", "ICX", listOf("purchase", "exchange", "transfer"), "B"),
        CurrencyEntity("ATOM", "Cosmos", "ATOM", listOf("purchase", "exchange", "transfer"), "B"),
        CurrencyEntity("ZRX", "0x", "ZRX", listOf("purchase", "exchange", "transfer", "deposit", "withdraw"), "B"),
        CurrencyEntity("MCO", "Crypto.com", "MCO", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "B"),
        CurrencyEntity("ONE", "Harmony", "ONE", listOf("purchase", "exchange", "transfer"), "B"),
        CurrencyEntity("KNC", "KyberNetwork - Deprecated", "KNC", listOf("deposit", "purchase", "exchange", "transfer", "withdraw"), "B"),
        CurrencyEntity("USDM", "USDM", "USDM", listOf("exchange", "transfer"), "B"),
        CurrencyEntity("CELR", "Celer", "CELR", listOf("purchase", "exchange", "transfer", "withdraw", "deposit"), "B")
    )
}