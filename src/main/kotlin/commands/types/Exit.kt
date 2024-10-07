package ru.itmo.commands.types

import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.view.ConsoleInterface

class Exit(private val console: ConsoleInterface) : Command(
    name = "exit",
    parameters = "",
    description = "Завершение программы"
) {
    override fun executeImpl(parameters: Array<String>): Response {
        console.printSuccessful("Завершение работы...")
        kotlin.system.exitProcess(777)
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.isEmpty()
}