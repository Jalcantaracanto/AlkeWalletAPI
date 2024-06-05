package com.example.alkeapi.data.response

data class AccountPost(
    val creationDate: String,
    val isBlocked: Boolean = false,
    val money: Int,
    val userId: Int
)