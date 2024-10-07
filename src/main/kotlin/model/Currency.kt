package ru.itmo.model

data class Currency(val name: String) {
    override fun toString(): String {
        return name
    }
}
