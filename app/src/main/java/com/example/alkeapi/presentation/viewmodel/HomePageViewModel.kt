package com.example.alkeapi.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.launch

class HomePageViewModel(private val alkeUseCase: AlkeUseCase, private val context: Context) :
    ViewModel() {

    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> = _user

    private val _account = MutableLiveData<AccountResponse>()
    val account: LiveData<AccountResponse> = _account

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun myProfile() {
        viewModelScope.launch {
            try {
                val user = alkeUseCase.myProfile()
                _user.value = user
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun myAccount() {
        viewModelScope.launch {
            try {
                val account = alkeUseCase.myAccount()
                _account.value = account[0]
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}