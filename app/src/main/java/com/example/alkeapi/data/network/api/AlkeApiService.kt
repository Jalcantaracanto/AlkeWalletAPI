package com.example.alkeapi.data.network.api

import com.example.alkeapi.data.model.Login
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AlkeApiService {

    @Headers("Content-type:application/json")
    @POST("auth/login")
    suspend fun login(@Body data: Login): LoginResponse

    @GET("users")
    suspend fun getAllUsers(@Query ("token") token: String) : MutableList<User>


}