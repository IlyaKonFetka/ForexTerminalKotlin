package ru.itmo.service.entity

class User : EntityHaveBalance() {
    private val transactionHistory = mutableListOf<String>()

    fun addTransaction(transaction: String) {
        transactionHistory += transaction
    }

    fun getTransactionHistory(): List<String> = transactionHistory.toList()

    fun getTransactionHistory(limit: Int): List<String> =
        transactionHistory.takeLast(limit)
}

