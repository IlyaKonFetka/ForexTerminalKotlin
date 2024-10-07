package ru.itmo.service.entity

import ru.itmo.model.Currency
import ru.itmo.model.CurrencyPair
import ru.itmo.service.exceptions.NoSuchCurrencyException
import ru.itmo.service.exceptions.UnavailableCurrencyPair
import java.util.*

class Terminal : EntityHaveBalance() {
    private val availableCurrencyPairs: MutableList<CurrencyPair> = ArrayList()

    fun getAvailableCurrencyPairs(): List<CurrencyPair> = availableCurrencyPairs

    @Throws(NoSuchCurrencyException::class)
    fun registerCurrencyPair(vararg pairs: CurrencyPair) {
        pairs.forEach { pair ->
            if (pair.baseCurrency !in balance || pair.quoteCurrency !in balance) {
                throw NoSuchCurrencyException()
            }
            availableCurrencyPairs += pair
        }
    }

    @Throws(UnavailableCurrencyPair::class)
    fun getCurrencyPair(currency1: Currency, currency2: Currency): CurrencyPair =
        availableCurrencyPairs.find { it.matchesPair(currency1, currency2) }
            ?: throw UnavailableCurrencyPair()

    fun updateCurrencyRates() {
        val random = Random()
        availableCurrencyPairs.forEach { pair ->
            val percentageChange = -5f + random.nextFloat() * 10f
            val coefficient = 1f + (percentageChange / 100f)
            pair.updateExchangeRate(coefficient)
        }
    }
}

