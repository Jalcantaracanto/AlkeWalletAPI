package com.example.alkeapi.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.domain.DatabaseUseCase

class HomePageViewModelFactory(
    private val alkeUseCase: AlkeUseCase,
    private val databaseUseCase: DatabaseUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomePageViewModel(alkeUseCase, databaseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}