package com.example.alkeapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alkeapi.data.local.model.Transaction

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): MutableList<Transaction>

}