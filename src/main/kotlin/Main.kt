package ru.itmo

import ru.itmo.commands.CommandManager
import ru.itmo.commands.types.*
import ru.itmo.controller.Controller
import ru.itmo.model.Currency
import ru.itmo.model.CurrencyPair
import ru.itmo.service.ExchangeService
import ru.itmo.service.entity.Terminal
import ru.itmo.service.entity.User
import ru.itmo.view.SimpleConsole
import java.util.*

fun main() {
    val console = SimpleConsole(Scanner(System.`in`))

    val USD = Currency("USD")
    val RUB = Currency("RUB")
    val EUR = Currency("EUR")
    val USDT = Currency("USDT")
    val BTC = Currency("BTC")

    val user = User().apply {
        registerCurrency(USD, RUB, EUR, USDT, BTC)
        runCatching { deposit(RUB, 1_000_000.0) }
    }

    val terminal = Terminal().apply {
        registerCurrency(USD, RUB, EUR, USDT, BTC)
        runCatching {
            deposit(RUB, 10_000.0)
            deposit(USD, 1_000.0)
            deposit(EUR, 1_000.0)
            deposit(USDT, 1_000.0)
            deposit(BTC, 1.5)
        }
    }

    val RUB_USD = CurrencyPair(RUB, USD, 0.010997f)
    val RUB_EUR = CurrencyPair(RUB, EUR, 0.009921f)
    val USD_EUR = CurrencyPair(USD, EUR, 0.90275f)
    val USD_USDT = CurrencyPair(USD, USDT, 1f)
    val USD_BTC = CurrencyPair(USD, BTC, 0.000017f)

    runCatching {
        terminal.registerCurrencyPair(RUB_USD, RUB_EUR, USD_EUR, USD_USDT, USD_BTC)
    }

    val exchangeService = ExchangeService(terminal, user)

    val commandManager = CommandManager()
    val balance = Balance(user)
    val buy = Buy(exchangeService)
    val deposit = Deposit(user)
    val exit = Exit(console)
    val help = Help(commandManager)
    val history = History(user)
    val rates = Rates(terminal)
    val sell = Sell(exchangeService)
    val withdraw = Withdraw(user)

    commandManager.registerCommand(balance, buy, deposit, exit, help, history, rates, sell, withdraw)

    val controller = Controller(console, commandManager, user, terminal)
    controller.start()
}
