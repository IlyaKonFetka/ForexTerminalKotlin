package ru.itmo.controller

import ru.itmo.commands.CommandManager
import ru.itmo.commands.exceptions.NumberOfArguments
import ru.itmo.commands.interfaces.MonetaryTransaction
import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response
import ru.itmo.service.entity.Terminal
import ru.itmo.service.entity.User
import ru.itmo.view.ConsoleInterface

class Controller(
    private val console: ConsoleInterface,
    private val commandManager: CommandManager,
    private val user: User,
    private val terminal: Terminal
) {

    private fun greeting() {
        console.println("""
            Добро пожаловать в ForexTerminal!
            Здесь вы управляете своими финансами.
            Введите help, чтобы получить список доступных команд.
        """.trimIndent())
    }

    fun start() {
        greeting()
        while (true) {
            console.prompt()

            val commandName = console.read().trim()
            val input = console.readln().trim()
            val parameters = input.takeIf { it.isNotEmpty() }?.split("\\s+".toRegex())?.toTypedArray() ?: emptyArray()

            when {
                commandName.isEmpty() -> continue
                commandName == EXIT_COMMAND -> return
                else -> {
                    prepareCommand(commandName, parameters)?.let { response ->
                        console.println(response.textBody)
                        if (response.status) {
                            console.printSuccessful(response.textStatus)
                        } else {
                            console.printError(response.textStatus)
                        }
                    }
                }
            }
        }
    }

    fun prepareCommand(commandName: String, parameters: Array<String>): Response? {
        val command = commandManager.getCommand(commandName, parameters) as? Command ?: run {
            console.printWarning("Команда $commandName не найдена. Введи help, чтобы получить список команд")
            return null
        }

        return try {
            command.execute(parameters).also { response ->
                if (response.status && command is MonetaryTransaction) {
                    user.addTransaction(command.makeKeyPhrase(parameters))
                    terminal.updateCurrencyRates()
                }
            }
        } catch (e: NumberOfArguments) {
            console.printError("Неверное количество аргументов: ${parameters.size}")
            console.println("Введите команду в формате:")
            console.println("${command.name} ${command.parameters}")
            null
        }
    }

    companion object {
        const val EXIT_COMMAND = "exit"
    }

}

