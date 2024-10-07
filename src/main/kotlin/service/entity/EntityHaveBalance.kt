package ru.itmo.service.entity

import ru.itmo.model.Currency
import ru.itmo.service.exceptions.NoMoneyException
import ru.itmo.service.exceptions.NoSuchCurrencyException

abstract class EntityHaveBalance {
    protected val balance: MutableMap<Currency, Double> = LinkedHashMap()

    val readOnlyBalance: Map<Currency, Double>
        get() = balance.toMap()

    fun registerCurrency(vararg currencies: Currency) {
        currencies.forEach { currency ->
            balance.putIfAbsent(currency, 0.0)
        }
    }

    fun getAvailableCurrencies(): Set<Currency> = balance.keys

    @Throws(NoSuchCurrencyException::class)
    fun deposit(currency: Currency, amount: Double) {
        balance[currency]?.let {
            balance[currency] = it + amount
        } ?: throw NoSuchCurrencyException()
    }

    @Throws(NoSuchCurrencyException::class, NoMoneyException::class)
    fun withdraw(currency: Currency, amount: Double) {
        val currentBalance = balance[currency] ?: throw NoSuchCurrencyException()
        if (currentBalance >= amount) {
            balance[currency] = currentBalance - amount
        } else {
            throw NoMoneyException()
        }
    }
}


