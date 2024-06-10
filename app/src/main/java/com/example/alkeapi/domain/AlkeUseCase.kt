package com.example.alkeapi.domain

import com.example.alkeapi.data.local.model.User
import com.example.alkeapi.data.network.repository.AlkeRepositoryImplement
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkeapi.data.network.response.UserDataResponse
import com.example.alkeapi.data.network.response.UserPost

class AlkeUseCase(private val alkeRepository: AlkeRepositoryImplement) {

    suspend fun login(email: String, password: String): String {
        return alkeRepository.login(email, password)
    }

    suspend fun myProfile(): UserDataResponse {
        return alkeRepository.myProfile()
    }

    suspend fun myAccount(): MutableList<AccountDataResponse> {
        return alkeRepository.myAccount()
    }

    suspend fun myTransactions(): MutableList<TransactionDataResponse> {
        return alkeRepository.myTransactions()
    }

    suspend fun createUser(user: UserPost): Boolean {
        return alkeRepository.createUser(user)
    }

    suspend fun createAccount(account: AccountPost): Boolean {
        return alkeRepository.createAccount(account)
    }

    suspend fun getUserById(id: Int): UserDataResponse {
        return alkeRepository.getUserById(id)
    }

    suspend fun getAllUsers(): MutableList<User> {
        return alkeRepository.getAllUsers()
    }


    suspend fun getAccountById(id: Int): AccountDataResponse {
        return alkeRepository.getAccountsById(id)
    }

    suspend fun createTransaction(transaction: TransactionPost): Boolean {
        return alkeRepository.createTransaction(transaction)
    }

    suspend fun getAllAccounts(): MutableList<AccountDataResponse> {
        return alkeRepository.getAllAccounts()
    }
}