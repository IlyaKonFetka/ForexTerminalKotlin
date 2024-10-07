package ru.itmo.commands.types

import ru.itmo.commands.CommandManager
import ru.itmo.commands.model.Command
import ru.itmo.commands.model.Response

class Help(private val commandManager: CommandManager) : Command(
    name = "help",
    parameters = "",
    description = "Список доступных команд"
) {
    override fun executeImpl(parameters: Array<String>): Response {
        val commandMap = commandManager.getCommandRegistry()
        if (commandMap.isEmpty()) {
            return Response(false, "Список не выведен", "Доступных команд не обнаружено")
        }
        val stringBuilder = StringBuilder("Список доступных команд:\n")
        for ((_, command) in commandMap) {
            stringBuilder.append("%-15s\t%s\n".format(command.name, command.description))
        }
        return Response(true, "Список команд успешно выведен", stringBuilder.toString())
    }

    override fun checkParameters(parameters: Array<String>): Boolean = parameters.isEmpty()
}
