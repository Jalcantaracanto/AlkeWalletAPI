package com.example.alkeapi.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.TransactionDataResponse
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel(private val alkeUseCase: AlkeUseCase, private val context: Context) :
    ViewModel() {

    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> = _user

    private val _account = MutableLiveData<AccountResponse>()
    val account: LiveData<AccountResponse> = _account

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _transactions = MutableLiveData<MutableList<TransactionDataResponse>>()
    val transactions: LiveData<MutableList<TransactionDataResponse>> get() = _transactions

    private val _userAccount = MutableLiveData<AccountResponse>()
    val userAccount: LiveData<AccountResponse> = _userAccount

    private val _userTransaction = MutableLiveData<UserDataResponse>()
    val userTransaction: LiveData<UserDataResponse> = _userTransaction

    init {
        myTransactions()
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

    fun myTransactions() {
        viewModelScope.launch {
            val transactions = withContext(Dispatchers.IO) {
                try {
                    alkeUseCase.myTransactions()
                } catch (e: Exception) {
                    throw e
                }
            }
            Log.d("TESTTRANSACTIONS", transactions.toString())
            _transactions.postValue(transactions)
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                val userTransaction = alkeUseCase.getUserById(id)
                _userTransaction.value = userTransaction
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getAccountById(id: Int) {
        viewModelScope.launch {
            try {
                val account = alkeUseCase.getAccountById(id)
                _userAccount.value = account
            } catch (e: Exception) {
                throw e
            }
        }

    }
}