package ru.itmo.commands.model

import ru.itmo.commands.exceptions.NumberOfArguments
import ru.itmo.commands.interfaces.Describable
import ru.itmo.commands.interfaces.Executable

abstract class Command(
    override val name: String,
    override val parameters: String,
    override val description: String
) : Describable, Executable {

    @Throws(NumberOfArguments::class)
    override fun execute(parameters: Array<String>): Response {
        if (!checkParameters(parameters)) throw NumberOfArguments()
        return executeImpl(parameters)
    }

    protected abstract fun executeImpl(parameters: Array<String>): Response

}

