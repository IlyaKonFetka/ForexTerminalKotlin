package ru.itmo.commands.interfaces

interface MonetaryTransaction {
    fun makeKeyPhrase(parameters: Array<String>): String
}