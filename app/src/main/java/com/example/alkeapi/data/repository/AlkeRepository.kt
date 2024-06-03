package com.example.alkeapi.data.repository

import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.UserDataResponse

interface AlkeRepository {

    suspend fun login(email: String, password: String): String

    suspend fun myProfile(): UserDataResponse

    suspend fun myAccount(): MutableList<AccountResponse>

    suspend fun getAllUsers(): MutableList<User>

}