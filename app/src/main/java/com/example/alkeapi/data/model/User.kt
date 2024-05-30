package com.example.alkeapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val first_name: String = "",
    val last_name: String = "",
    val email: String = "",
    val password: String = ""
)