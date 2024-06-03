package com.example.alkeapi.presentation.view.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alkeapi.R
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStop() {
        super.onStop()
        SharedPreferencesHelper.clearToken(this)
        SharedPreferencesHelper.clearUserData(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesHelper.clearToken(this)
        SharedPreferencesHelper.clearUserData(this)
    }
}