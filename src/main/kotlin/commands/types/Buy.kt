package ru.itmo.commands.types

import ru.itmo.commands.interfaces.MonetaryTransaction
import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.model.Currency
import ru.itmo.service.ExchangeService
import ru.itmo.service.exceptions.*

class Buy(private val exchangeService: ExchangeService) : Command(
    name = "buy",
    parameters = "<amount_to_buy> <currency_to_sell> <currency_to_buy>",
    description = "Покупка валюты"
), MonetaryTransaction {

    override fun executeImpl(parameters: Array<String>): Response {
        return try {
            exchangeService.makeBuyTransaction(
                Currency(parameters[1]),
                Currency(parameters[2]),
                parameters[0].toDouble()
            )
            Response(true, "Обмен успешно совершён", "")
        } catch (e: Exception) {
            when (e) {
                is UnavailableCurrencyPair -> Response(false, "Покупка не состоялась",
                    "Терминал не осуществляет обмен данных валют или валюты введены неверно")
                is NoSuchCurrencyUser -> Response(false, "Покупка не состоялась",
                    "Вы не храните средства в этой валюте или валюта введена неверно")
                is NoSuchCurrencyTerminal -> Response(false, "Покупка не состоялась",
                    "Терминал не хранит средства в этой валюте или валюта введена неверно")
                is NoMoneyTerminal -> Response(false, "Покупка не состоялась",
                    "У терминала недостаточно средств в выбранной валюте для совершения обмена")
                is NoMoneyUser -> Response(false, "Покупка не состоялась",
                    "У Вас недостаточно средств в выбранной валюте для совершения обмена")
                is NumberFormatException -> Response(false, "Покупка не состоялась",
                    "Сумма обмена введена неверно")
                else -> throw e
            }
        }
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.size == 3

    override fun makeKeyPhrase(parameters: Array<String>): String =
        "Покупка ${parameters[0]} ${parameters[2]} в обмен на ${parameters[1]}"
}