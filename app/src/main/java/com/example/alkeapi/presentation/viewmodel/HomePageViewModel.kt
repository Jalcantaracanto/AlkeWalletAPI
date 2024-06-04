package com.example.alkeapi.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alkeapi.data.response.AccountDataResponse
import com.example.alkeapi.data.response.AccountResponse
import com.example.alkeapi.data.response.TransactionDataResponse
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.domain.AlkeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePageViewModel(private val alkeUseCase: AlkeUseCase) :
    ViewModel() {

    private val _user = MutableLiveData<UserDataResponse>()
    val user: LiveData<UserDataResponse> = _user

    private val _account = MutableLiveData<AccountDataResponse>()
    val account: LiveData<AccountDataResponse> = _account

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _transactions = MutableLiveData<MutableList<TransactionDataResponse>>()
    val transactions: LiveData<MutableList<TransactionDataResponse>> get() = _transactions


    private val _userAccount = MutableLiveData<AccountDataResponse>()
    val userAccount: LiveData<AccountDataResponse> = _userAccount

    private val _userTransaction = MutableLiveData<UserDataResponse>()
    val userTransaction: LiveData<UserDataResponse> = _userTransaction

    private val _userById = MutableLiveData<MutableList<UserDataResponse>>()
    val userById: LiveData<MutableList<UserDataResponse>> get() = _userById

    init {
        _userById.value = mutableListOf()
        myTransactions()
        myProfile()
        myAccount()
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
            try {
                val transactions = withContext(Dispatchers.IO) {
                    alkeUseCase.myTransactions()
                }
                Log.d("TESTTRANSACTIONS", transactions.toString())
                _transactions.postValue(transactions)

                transactions.forEach { transaction ->
                    getUserById(transaction.userId)
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            try {
                val userTransaction = alkeUseCase.getUserById(id)
                _userById.value?.let {
                    it.add(userTransaction)
                    _userById.postValue(it)
                }
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