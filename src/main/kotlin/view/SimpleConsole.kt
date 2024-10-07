package ru.itmo.view

import java.util.*

class SimpleConsole(private val scanner: Scanner) : ConsoleInterface {

    companion object {
        private const val P = ">: "
    }

    override fun prompt() {
        print(P)
    }

    override fun print(obj: Any?) {
        kotlin.io.print(obj)
    }

    override fun println(obj: Any?) {
        kotlin.io.println(obj)
    }

    override fun read(): String {
        return scanner.next()
    }

    override fun readln(): String {
        return scanner.nextLine()
    }

    override fun isCanRead(): Boolean {
        return scanner.hasNext()
    }

    override fun isCanReadln(): Boolean {
        return scanner.hasNextLine()
    }

    override fun printWarning(obj: Any?) {
        val brightYellowColorCode = "\u001B[33;1m"
        val resetColorCode = "\u001B[0m"
        kotlin.io.println("$brightYellowColorCode$obj$resetColorCode")
    }

    override fun printSuccessful(obj: Any?) {
        val brightGreenColorCode = "\u001B[32;1m"
        val resetColorCode = "\u001B[0m"
        kotlin.io.println("$brightGreenColorCode$obj$resetColorCode")
    }

    override fun printError(obj: Any?) {
        val brightRedColorCode = "\u001B[31;1m"
        val resetColorCode = "\u001B[0m"
        kotlin.io.println("$brightRedColorCode$obj$resetColorCode")
    }
}
