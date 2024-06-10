package com.example.alkeapi.presentation.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.domain.AlkeUseCase

class ProfilePageViewModelFactory(private val alkeUseCase: AlkeUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfilePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfilePageViewModel(alkeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}