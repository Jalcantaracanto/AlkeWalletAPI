package com.example.alkeapi.domain

import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.repository.AlkeRepositoryImplement
import com.example.alkeapi.data.response.LoginResponse

class AlkeUseCase(private val alkeRepository: AlkeRepositoryImplement) {

    suspend fun login(email: String, password: String) : String {
        return alkeRepository.login(email, password)
    }

    suspend fun getAllUsers(token: String) : MutableList<User> {
        return alkeRepository.getAllUsers(token)
    }
}