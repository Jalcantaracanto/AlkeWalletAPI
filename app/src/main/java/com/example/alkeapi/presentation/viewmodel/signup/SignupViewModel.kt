package com.example.alkeapi.presentation.viewmodel.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.data.network.response.AccountPost
import com.example.alkeapi.data.network.response.UserPost
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.launch

class SignupViewModel(private val alkeUseCase: AlkeUseCase) :
    ViewModel() {

    private val _createAccountResult = MutableLiveData<Boolean>()
    val createAccountResult: LiveData<Boolean> get() = _createAccountResult

    private val _createUserResult = MutableLiveData<Boolean>()
    val createUserResult: LiveData<Boolean> get() = _createUserResult

    fun createAccount(account: AccountPost) {
        viewModelScope.launch {
            val result = alkeUseCase.createAccount(account)
            _createAccountResult.value = result
        }
    }

    fun createUser(user: UserPost) {
        viewModelScope.launch {
            val result = alkeUseCase.createUser(user)
            _createUserResult.value = result
        }
    }

}