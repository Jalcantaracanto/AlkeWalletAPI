package com.example.alkeapi.data.response

data class AccountResponse(
    val createdAt: String,
    val creationDate: String,
    val id: Int,
    val isBlocked: Boolean,
    val money: String,
    val updatedAt: String,
    val userId: Int
)