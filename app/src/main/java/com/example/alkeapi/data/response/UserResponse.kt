package com.example.alkeapi.data.response

import com.example.alkeapi.data.model.User

data class UserResponse(
    val previousPage: String?,
    val nextPage: String?,
    val data: MutableList<User>
)