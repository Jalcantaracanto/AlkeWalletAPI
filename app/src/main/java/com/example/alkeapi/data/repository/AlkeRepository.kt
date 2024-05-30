package com.example.alkeapi.data.repository

import com.example.alkeapi.data.model.User

interface AlkeRepository {

    suspend fun login(email: String, password: String) : String

    suspend fun getAllUsers(token : String) : MutableList<User>

}