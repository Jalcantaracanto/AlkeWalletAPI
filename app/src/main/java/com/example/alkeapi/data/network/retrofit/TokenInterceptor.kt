package com.example.alkeapi.data.network.retrofit

import android.content.Context
import com.example.alkeapi.application.SharedPreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context : Context) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = SharedPreferencesHelper.getToken(context)
        val request = chain.request().newBuilder()

        token?.let{
            request.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }

}