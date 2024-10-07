package ru.itmo.service

import ru.itmo.model.Currency
import ru.itmo.service.entity.Terminal
import ru.itmo.service.entity.User
import ru.itmo.service.exceptions.*

class ExchangeService(private val terminal: Terminal, private val user: User) {

    private fun userToTerminal(currency: Currency, amount: Double) {
        try {
            user.withdraw(currency, amount)
        } catch (e: NoSuchCurrencyException) {
            throw NoSuchCurrencyUser()
        }

        try {
            terminal.deposit(currency, amount)
        } catch (e: NoSuchCurrencyException) {
            throw NoSuchCurrencyTerminal()
        }
    }

    private fun terminalToUser(currency: Currency, amount: Double) {
        try {
            terminal.withdraw(currency, amount)
        } catch (e: NoSuchCurrencyException) {
            throw NoSuchCurrencyTerminal()
        }

        try {
            user.deposit(currency, amount)
        } catch (e: NoSuchCurrencyException) {
            throw NoSuchCurrencyUser()
        }
    }

    @Throws(
        UnavailableCurrencyPair::class,
        NoMoneyTerminal::class,
        NoMoneyUser::class,
        NoSuchCurrencyTerminal::class,
        NoSuchCurrencyUser::class
    )
    fun makeBuyTransaction(currencyFrom: Currency, currencyTo: Currency, fromTerminal: Double) {
        val currencyPair = terminal.getCurrencyPair(currencyFrom, currencyTo)
        val rate = currencyPair.getUniversalRate(currencyFrom, currencyTo)
        val fromUser = fromTerminal / rate

        try {
            terminalToUser(currencyTo, fromTerminal)
        } catch (e: NoMoneyException) {
            throw NoMoneyTerminal()
        }

        try {
            userToTerminal(currencyFrom, fromUser)
        } catch (e: NoMoneyException) {
            throw NoMoneyUser()
        }
    }

    @Throws(
        UnavailableCurrencyPair::class,
        NoMoneyTerminal::class,
        NoMoneyUser::class,
        NoSuchCurrencyUser::class,
        NoSuchCurrencyTerminal::class
    )
    fun makeSellTransaction(currencyFrom: Currency, currencyTo: Currency, fromUser: Double) {
        val currencyPair = terminal.getCurrencyPair(currencyFrom, currencyTo)
        val rate = currencyPair.getUniversalRate(currencyFrom, currencyTo)
        val fromTerminal = fromUser * rate

        try {
            terminalToUser(currencyTo, fromTerminal)
        } catch (e: NoMoneyException) {
            throw NoMoneyTerminal()
        }

        try {
            userToTerminal(currencyFrom, fromUser)
        } catch (e: NoMoneyException) {
            throw NoMoneyUser()
        }
    }
}
