package com.example.alkeapi.data.repository

import android.content.Context
import com.example.alkeapi.data.model.Login
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.TransactionDataResponse
import com.example.alkeapi.data.response.UserDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlkeRepositoryImplement(
    private val apiService: AlkeApiService,
    private val context: Context
) : AlkeRepository {


    override suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val loginRequest = Login(email, password)
            val response = apiService.login(loginRequest)
            response.accessToken
        }
    }

    override suspend fun myProfile(): UserDataResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.myProfile()
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun myAccount(): MutableList<AccountResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.myAccount()
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun myTransactions(): MutableList<TransactionDataResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.myTransactions()
                response.data
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getUserById(id: Int): UserDataResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUserById(id)
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getAccountsById(id: Int): AccountResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAccountById(id)
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun getAllUsers(): MutableList<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAllUsers()
                response.data
            } catch (e: Exception) {
                throw e
            }
        }
    }

}