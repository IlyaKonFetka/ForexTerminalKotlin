package ru.itmo.commands.types

import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.service.entity.Terminal
import java.text.DecimalFormat

class Rates(private val terminal: Terminal) : Command(
    name = "rates",
    parameters = "",
    description = "Просмотр курсов валют"
) {
    override fun executeImpl(parameters: Array<String>): Response {
        val pairs = terminal.getAvailableCurrencyPairs()
        if (pairs.isEmpty()) {
            return Response(false, "Список не выведен", "У терминала нет доступных валютных пар для обмена")
        }

        val decimalFormat = DecimalFormat("#.######")
        val stringBuilder = StringBuilder("Список доступных валютных пар:\n")

        for (pair in pairs) {
            val (currencyPair, rate) = pair.toString().split(":").map { it.trim() }
            val formattedRate = decimalFormat.format(rate.toDouble())
            stringBuilder.append("%-10s: %s\n".format(currencyPair, formattedRate))
        }

        return Response(true, "Список успешно выведен", stringBuilder.toString())
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.isEmpty()
}
