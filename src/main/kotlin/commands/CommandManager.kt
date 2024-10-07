package ru.itmo.commands

import ru.itmo.commands.interfaces.Executable
import ru.itmo.commands.model.Command

class CommandManager {
    private val commandRegistry: MutableMap<String, Command> = mutableMapOf()

    fun registerCommand(vararg commands: Command) {
        for (command in commands) {
            if (commandRegistry.containsKey(command.name)) {
                throw IllegalArgumentException("Команда с именем ${command.name} уже зарегистрирована.")
            }
            commandRegistry[command.name] = command
        }
    }

    fun getCommand(commandName: String, userInput: Array<String>): Executable? {
        return commandRegistry[commandName]
    }

    fun getCommandRegistry(): Map<String, Command> {
        return commandRegistry
    }
}
