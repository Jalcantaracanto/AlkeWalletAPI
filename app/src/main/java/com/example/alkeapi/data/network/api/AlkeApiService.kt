package com.example.alkeapi.data.network.api

import com.example.alkeapi.data.model.Login
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.LoginResponse
import com.example.alkeapi.data.response.TransactionResponse
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.data.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AlkeApiService {

    @Headers("Content-type:application/json")
    @POST("auth/login")
    suspend fun login(@Body data: Login): LoginResponse

    @GET("auth/me")
    suspend fun myProfile(): UserDataResponse

    @GET("accounts/me")
    suspend fun myAccount(): MutableList<AccountResponse>

    @GET("transactions")
    suspend fun myTransactions(): TransactionResponse

    @GET("users")
    suspend fun getAllUsers(): UserResponse

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDataResponse

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountResponse



}