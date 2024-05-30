package com.example.alkeapi.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.R
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.repository.AlkeRepositoryImplement
import com.example.alkeapi.databinding.FragmentLoginBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.presentation.viewmodel.LoginViewModel
import com.example.alkeapi.presentation.viewmodel.LoginViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit().create(AlkeApiService::class.java)
        val alkeRepository = AlkeRepositoryImplement(alkeApiService, context)
        val alkeUseCase = AlkeUseCase(alkeRepository)
        val loginViewModelFactory = LoginViewModelFactory(alkeUseCase, context)

        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        observeViewModel()

        binding.btnLogin.setOnClickListener {
            login()
        }


        binding.btnGetUsers.setOnClickListener{
            val token = loginViewModel.loginResult.value
            if (token != null) {
                loginViewModel.getAllUsers(token)

            }else{
                binding.txtToken.text = "Please login first"
            }
        }

    }

    private fun observeViewModel() {
        loginViewModel.loginResult.observe(viewLifecycleOwner) { token ->
            binding.txtToken.text = token
        }

        loginViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            binding.txtToken.text = errorMessage
        }

        loginViewModel.usersResult.observe(viewLifecycleOwner) { users ->
            users.forEach { user ->
                Log.d("USERTEST", users.toString())
            }
        }
    }

    private fun login() {
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginViewModel.login(email, password)
        } else {
            binding.txtToken.text = "Please enter both email and password"
        }
    }
}