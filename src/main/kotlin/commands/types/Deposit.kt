package ru.itmo.commands.types

import ru.itmo.commands.interfaces.MonetaryTransaction
import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.model.Currency
import ru.itmo.service.entity.User
import ru.itmo.service.exceptions.NoSuchCurrencyException

class Deposit(private val user: User) : Command(
    name = "deposit",
    parameters = "<amount> <currency>",
    description = "Пополнение баланса"
), MonetaryTransaction {

    override fun executeImpl(parameters: Array<String>): Response {
        return try {
            user.deposit(Currency(parameters[1]), parameters[0].toDouble())
            Response(true, "Баланс успешно пополнен", "")
        } catch (e: Exception) {
            when (e) {
                is NoSuchCurrencyException -> Response(false, "Пополнения не произошло",
                    "У вас отсутствует счёт в данной валюте или валюта была введена неверно")
                is NumberFormatException -> Response(false, "Пополнения не произошло",
                    "Неверный формат суммы средств")
                else -> throw e
            }
        }
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.size == 2

    override fun makeKeyPhrase(parameters: Array<String>): String =
        "Пополнение баланса на ${parameters[0]} ${parameters[1]}"
}
