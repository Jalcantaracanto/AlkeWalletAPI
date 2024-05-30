package com.example.alkeapi.data.network.api

import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.data.model.Login
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.response.LoginResponse

class AlkeApiClient {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun login(email: String, password: String): LoginResponse {
        val response = retrofit.create(AlkeApiService::class.java)
        val loginData = Login(email, password)
        return response.login(loginData)
    }

    suspend fun getAllUsers(token : String) : MutableList<User>{
        val response = retrofit.create(AlkeApiService::class.java).getAllUsers(token)
        return response
    }

}