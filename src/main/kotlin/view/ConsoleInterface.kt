package ru.itmo.view

interface ConsoleInterface {
    fun prompt()
    fun print(obj: Any?)
    fun println(obj: Any?)
    fun read(): String
    fun readln(): String
    fun isCanRead(): Boolean
    fun isCanReadln(): Boolean
    fun printSuccessful(obj: Any?)
    fun printWarning(obj: Any?)
    fun printError(obj: Any?)
}