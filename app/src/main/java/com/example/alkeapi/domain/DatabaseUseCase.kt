package com.example.alkeapi.domain

import com.example.alkeapi.data.local.model.Transaction
import com.example.alkeapi.data.local.repository.TransactionR

class DatabaseUseCase(private val transactionR: TransactionR) {

    suspend fun insertTransaction(transaction: Transaction) =
        transactionR.insertTransaction(transaction)

    suspend fun getAllTransaction() = transactionR.getAllTransactions()


}