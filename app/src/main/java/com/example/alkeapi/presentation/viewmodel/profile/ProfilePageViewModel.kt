package com.example.alkeapi.presentation.viewmodel.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.data.network.response.UserDataResponse
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.launch

class ProfilePageViewModel(private val alkeUseCase: AlkeUseCase) :
    ViewModel() {

    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        myProfile()
    }

    fun myProfile() {
        viewModelScope.launch {
            try {
                val user = alkeUseCase.myProfile()
                _user.value = user
                Log.d("MyProfile", "myProfile: $user")
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}