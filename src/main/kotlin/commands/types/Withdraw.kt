package ru.itmo.commands.types

import ru.itmo.commands.interfaces.MonetaryTransaction
import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.model.Currency
import ru.itmo.service.entity.User
import ru.itmo.service.exceptions.NoMoneyException
import ru.itmo.service.exceptions.NoSuchCurrencyException

class Withdraw(private val user: User) : Command(
    name = "withdraw",
    parameters = "<amount> <currency>",
    description = "Снятие средств"
), MonetaryTransaction {

    override fun executeImpl(parameters: Array<String>): Response {
        return try {
            user.withdraw(Currency(parameters[1]), parameters[0].toDouble())
            Response(true, "Вывод средств успешен", "")
        } catch (e: Exception) {
            when (e) {
                is NoSuchCurrencyException -> Response(false, "Вывод не состоялся",
                    "Вы не храните средства в этой валюте или валюта введена неверно")
                is NoMoneyException -> Response(false, "Вывод не состоялся",
                    "У Вас недостаточно средств в выбранной валюте для совершения вывода")
                is NumberFormatException -> Response(false, "Покупка не состоялась",
                    "Сумма обмена введена неверно")
                else -> throw e
            }
        }
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.size == 2

    override fun makeKeyPhrase(parameters: Array<String>): String =
        "Вывод ${parameters[0]} ${parameters[1]}"
}
