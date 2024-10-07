package ru.itmo.model

import ru.itmo.service.exceptions.UnavailableCurrencyPair

class CurrencyPair(
    val baseCurrency: Currency,
    val quoteCurrency: Currency,
    var exchangeRate: Float
) {

    override fun toString(): String {
        return "$baseCurrency/$quoteCurrency: $exchangeRate"
    }

    fun matchesPair(currency1: Currency, currency2: Currency): Boolean {
        return (baseCurrency == currency1 && quoteCurrency == currency2) ||
                (baseCurrency == currency2 && quoteCurrency == currency1)
    }

    @Throws(UnavailableCurrencyPair::class)
    fun getUniversalRate(currencyFrom: Currency, currencyTo: Currency): Float {
        return when {
            currencyFrom == baseCurrency && currencyTo == quoteCurrency -> exchangeRate
            currencyFrom == quoteCurrency && currencyTo == baseCurrency -> 1 / exchangeRate
            else -> throw UnavailableCurrencyPair()
        }
    }

    fun updateExchangeRate(coefficient: Float) {
        exchangeRate *= coefficient
    }
}
