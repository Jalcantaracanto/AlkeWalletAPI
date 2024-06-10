package com.example.alkeapi.presentation.viewmodel.sendmoney

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.domain.DatabaseUseCase

class SendMoneyViewModelFactory(private val alkeUseCase: AlkeUseCase, private val databaseUseCase: DatabaseUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendMoneyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SendMoneyViewModel(alkeUseCase, databaseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}