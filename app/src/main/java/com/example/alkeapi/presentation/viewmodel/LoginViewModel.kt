package com.example.alkeapi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.launch
import android.content.Context
import android.util.Log
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.response.UserDataResponse

class LoginViewModel(private val alkeUseCase: AlkeUseCase, private val context: Context) :
    ViewModel() {

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
                myProfile()
                Log.d("TOKENTEST", "Login successful, token saved: $token")
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("TOKENTEST", "Login error", e)
            }
        }
    }

    fun myProfile() {
        viewModelScope.launch {
            try {
                val user = alkeUseCase.myProfile()
                SharedPreferencesHelper.idUserLogged(context, user.id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    fun getAllUsers() {
        viewModelScope.launch {
            try {
                val users = alkeUseCase.getAllUsers()
                _usersResult.value = users
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}