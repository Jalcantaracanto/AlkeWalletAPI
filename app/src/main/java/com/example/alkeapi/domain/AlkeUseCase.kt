package com.example.alkeapi.domain

import android.service.autofill.UserData
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.repository.AlkeRepositoryImplement
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.LoginResponse
import com.example.alkeapi.data.response.TransactionDataResponse
import com.example.alkeapi.data.response.TransactionResponse
import com.example.alkeapi.data.response.UserDataResponse

class AlkeUseCase(private val alkeRepository: AlkeRepositoryImplement) {

    suspend fun login(email: String, password: String): String {
        return alkeRepository.login(email, password)
    }

    suspend fun myProfile(): UserDataResponse {
        return alkeRepository.myProfile()
    }

    suspend fun myAccount(): MutableList<AccountResponse> {
        return alkeRepository.myAccount()
    }

    suspend fun myTransactions(): MutableList<TransactionDataResponse> {
        return alkeRepository.myTransactions()
    }

    suspend fun getUserById(id: Int): UserDataResponse {
        return alkeRepository.getUserById(id)
    }

    suspend fun getAllUsers(): MutableList<User> {
        return alkeRepository.getAllUsers()
    }


    suspend fun getAccountById(id: Int): AccountResponse {
        return alkeRepository.getAccountsById(id)
    }
}