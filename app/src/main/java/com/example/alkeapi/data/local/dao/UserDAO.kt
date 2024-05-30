package com.example.alkeapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alkeapi.data.model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users : MutableList<User>)

    @Query(value = "SELECT * FROM User")
    suspend fun getAllUsers() : MutableList<User>

    @Query(value = "SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id : Int) : User


}