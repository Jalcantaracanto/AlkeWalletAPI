package com.example.alkeapi.data.network.retrofit

import android.content.Context
import com.example.alkeapi.application.SharedPreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context : Context) :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = SharedPreferencesHelper.getToken(context)

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }

}