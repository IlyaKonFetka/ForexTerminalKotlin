package ru.itmo.commands.interfaces

import ru.itmo.commands.exceptions.NumberOfArguments
import ru.itmo.commands.model.Response

interface Executable {
    @Throws(NumberOfArguments::class)
    fun execute(parameters: Array<String>): Response

    fun checkParameters(parameters: Array<String>): Boolean
}