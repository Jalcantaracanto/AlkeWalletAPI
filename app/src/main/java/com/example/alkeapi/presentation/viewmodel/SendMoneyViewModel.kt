package com.example.alkeapi.presentation.viewmodel

import androidx.lifecycle.*
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.response.AccountDataResponse
import com.example.alkeapi.data.response.TransactionPost
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.launch

class SendMoneyViewModel(private val alkeUseCase: AlkeUseCase) : ViewModel() {

    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> get() = _user

    private val _account = MutableLiveData<AccountDataResponse>()
    val account: LiveData<AccountDataResponse> get() = _account

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _usersResult = MutableLiveData<MutableList<User>>()
    val usersResult: LiveData<MutableList<User>> get() = _usersResult

    private val _accountResult = MutableLiveData<MutableList<AccountDataResponse>>()
    val accountResult: LiveData<MutableList<AccountDataResponse>> get() = _accountResult

    private val _transactionResult = MutableLiveData<Boolean>()
    val transactionResult: LiveData<Boolean> get() = _transactionResult


    init {
        myProfile()
        myAccount()
        getAllUsers()
        getAllAccounts()
    }

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

    fun createTransaction(transaction: TransactionPost) {
        viewModelScope.launch {
            try {
                val result = alkeUseCase.createTransaction(transaction)
                _transactionResult.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getAllAccounts() {
        viewModelScope.launch {
            try {
                val allAccounts = alkeUseCase.getAllAccounts()
                val uniqueUserAccounts = allAccounts.groupBy { it.userId }.map { it.value.first() }
                _accountResult.value = uniqueUserAccounts.toMutableList()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

}