package com.example.alkeapi.data.network.repository

import com.example.alkeapi.data.local.model.User
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.AccountResponse
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkeapi.data.network.response.UserDataResponse
import com.example.alkeapi.data.network.response.UserPost

interface AlkeRepository {

    suspend fun login(email: String, password: String): String

    suspend fun myProfile(): UserDataResponse

    suspend fun myAccount(): MutableList<AccountDataResponse>

    suspend fun myTransactions(): MutableList<TransactionDataResponse>

    suspend fun createUser(user: UserPost): Boolean

    suspend fun getUserById(id: Int): UserDataResponse

    suspend fun createAccount(account: AccountPost): Boolean

    suspend fun getAccountsById(id: Int): AccountDataResponse

    suspend fun getAllUsers(): MutableList<User>

    suspend fun createTransaction(transaction: TransactionPost): Boolean

    suspend fun getAllAccounts(): MutableList<AccountDataResponse>

}