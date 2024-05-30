package com.example.alkeapi.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.domain.AlkeUseCase

class LoginViewModelFactory(private val alkeUseCase: AlkeUseCase, private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(alkeUseCase, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}