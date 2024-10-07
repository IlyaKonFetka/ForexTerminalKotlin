package ru.itmo.commands.types

import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.service.entity.User

class History(private val user: User) : Command(
    name = "history",
    parameters = "",
    description = "Просмотр истории транзакций"
) {
    override fun executeImpl(parameters: Array<String>): Response {
        val transactions = user.getTransactionHistory(8)
        if (transactions.isEmpty()) {
            return Response(false, "Список не выведен", "Операции ещё не производились")
        }
        val stringBuilder = StringBuilder("Список последних операций:\n")
        transactions.forEach { stringBuilder.append(it).append("\n") }
        return Response(true, "Список успешно выведен", stringBuilder.toString())
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.isEmpty()
}
