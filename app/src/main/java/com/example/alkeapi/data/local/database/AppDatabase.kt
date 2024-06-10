package com.example.alkeapi.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alkeapi.data.local.dao.TransactionDAO
import com.example.alkeapi.data.local.dao.UserDAO
import com.example.alkeapi.data.local.model.Transaction
import com.example.alkeapi.data.local.model.User


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [User::class, Transaction::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun userdao(): UserDAO
    abstract fun transactionDao(): TransactionDAO
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alkewallet_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}