package com.example.alkeapi.data.network.retrofit

import android.content.Context
import android.util.Log
import com.example.alkeapi.application.SharedPreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context : Context) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SharedPreferencesHelper.getToken(context)
        val request = if (token != null) {
            Log.d("TOKENINTERCEPTOR", "Token: $token") // Agregar log para verificar el token
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }

}