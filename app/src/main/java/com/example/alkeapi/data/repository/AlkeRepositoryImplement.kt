package com.example.alkeapi.data.repository

import android.content.Context
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.data.model.Login
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.network.api.AlkeApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlkeRepositoryImplement(private val apiService: AlkeApiService, private val context: Context) : AlkeRepository {



    override suspend fun login(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val loginRequest = Login(email, password)
            val response = apiService.login(loginRequest)
            response.accessToken
        }
    }

    override suspend fun getAllUsers(token : String): MutableList<User> {
        return withContext(Dispatchers.IO) {
            val tokenHelper = SharedPreferencesHelper.getToken(context)
            tokenHelper?.let{
                apiService.getAllUsers(it)
            }?: throw Exception("Token not found")
        }
    }

}