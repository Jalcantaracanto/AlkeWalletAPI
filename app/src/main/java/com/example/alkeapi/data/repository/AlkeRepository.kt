package com.example.alkeapi.data.repository

import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.response.AccountDataResponse
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.TransactionDataResponse
import com.example.alkeapi.data.response.TransactionPost
import com.example.alkeapi.data.response.UserDataResponse

interface AlkeRepository {

    suspend fun login(email: String, password: String): String

    suspend fun myProfile(): UserDataResponse

    suspend fun myAccount(): MutableList<AccountDataResponse>

    suspend fun myTransactions(): MutableList<TransactionDataResponse>

    suspend fun getUserById(id: Int): UserDataResponse

    suspend fun getAccountsById(id: Int): AccountDataResponse

    suspend fun getAllUsers(): MutableList<User>

    suspend fun createTransaction(transaction: TransactionPost): Boolean

    suspend fun getAllAccounts(): MutableList<AccountDataResponse>

}