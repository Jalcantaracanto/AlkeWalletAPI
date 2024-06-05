package com.example.alkeapi.data.network.api

import com.example.alkeapi.data.model.Login
import com.example.alkeapi.data.response.AccountDataResponse
import com.example.alkeapi.data.response.AccountPost
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.LoginResponse
import com.example.alkeapi.data.response.TransactionPost
import com.example.alkeapi.data.response.TransactionResponse
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.data.response.UserPost
import com.example.alkeapi.data.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface AlkeApiService {

    /**
     * Servicio De Login
     */

    @Headers("Content-type:application/json")
    @POST("auth/login")
    suspend fun login(@Body data: Login): LoginResponse

    /**
     * Servicios Perfil De Usuario
     */

    @GET("auth/me")
    suspend fun myProfile(): UserDataResponse

    @GET("accounts/me")
    suspend fun myAccount(): MutableList<AccountDataResponse>

    /**
     * Servicios De Usuarios
     */

    @POST("users")
    suspend fun createUser(@Body data: UserPost): Response<Void>

    @GET("users")
    suspend fun getAllUsers(): UserResponse

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDataResponse


    /**
     * Servicios De Cuenta
     */

    @POST("accounts")
    suspend fun createAccount(@Body data: AccountPost): Response<Void>

    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): AccountDataResponse

    @GET("accounts")
    suspend fun getAllAccounts(): AccountResponse


    /**
     * Servicios De Transacciones
     */

    @GET("transactions")
    suspend fun myTransactions(): TransactionResponse

    @POST("transactions")
    suspend fun createTransactions(@Body data: TransactionPost): Response<Void>

}