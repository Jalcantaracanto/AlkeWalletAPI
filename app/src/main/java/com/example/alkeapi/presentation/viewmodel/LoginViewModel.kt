package com.example.alkeapi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.launch
import android.content.Context
import com.example.alkeapi.data.model.User

class LoginViewModel(private val alkeUseCase: AlkeUseCase, private val context: Context) : ViewModel() {

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _usersResult = MutableLiveData<MutableList<User>>()
    val usersResult: LiveData<MutableList<User>> = _usersResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val token = alkeUseCase.login(email, password)
                SharedPreferencesHelper.saveToken(context, token)
                _loginResult.value = token
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getAllUsers(token: String) {
        viewModelScope.launch {
            try {
                val users = alkeUseCase.getAllUsers(token)
                _usersResult.value = users
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

}