package ru.itmo.commands.types

import ru.itmo.commands.exceptions.NumberOfArguments
import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.service.entity.User

class Balance(private val user: User) : Command(
    name = "balance",
    parameters = "",
    description = "Просмотр баланса"
) {

    override fun executeImpl(parameters: Array<String>): Response {
        val balance = user.readOnlyBalance
        val stringBuilder = StringBuilder("Ваш баланс:\n")

        return when {
            balance.isEmpty() -> Response(
                status = false,
                textStatus = "Баланс отсутствует",
                textBody = "Похоже, что вы не храните средства ни в одной валюте"
            )
            else -> {
                balance.forEach { (currency, amount) ->
                    stringBuilder.append("%-4s: %.2f\n".format(currency, amount))
                }
                Response(
                    status = true,
                    textStatus = "Баланс успешно выведен",
                    textBody = stringBuilder.toString()
                )
            }
        }
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.isEmpty()
}


