package com.example.alkeapi.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.domain.AlkeUseCase

class HomePageViewModelFactory(private val alkeUseCase: AlkeUseCase, private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomePageViewModel(alkeUseCase, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}