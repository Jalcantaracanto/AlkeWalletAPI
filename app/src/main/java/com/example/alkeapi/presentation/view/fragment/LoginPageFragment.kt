package com.example.alkeapi.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.alkeapi.R
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.network.repository.AlkeRepositoryImplement
import com.example.alkeapi.databinding.FragmentLoginPageBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.presentation.viewmodel.login.LoginViewModel
import com.example.alkeapi.presentation.viewmodel.login.LoginViewModelFactory

class LoginPageFragment : Fragment() {

    private lateinit var binding: FragmentLoginPageBinding
    private lateinit var loginViewModel: LoginViewModel
    private var hasNavigated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val alkeRepository = AlkeRepositoryImplement(alkeApiService)
        val alkeUseCase = AlkeUseCase(alkeRepository)
        val loginViewModelFactory = LoginViewModelFactory(alkeUseCase, context)

        loginViewModel = ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            login()
        }

        loginViewModel.userProfileSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                hasNavigated = true
                findNavController().navigate(R.id.homePageFragment)
            }
        }
    }

    private fun login() {
        val email = binding.textInputEmail.editText?.text.toString()
        val password = binding.textInputPassword.editText?.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            loginViewModel.login(email, password)
        } else {
            binding.textInputEmail.error = "Please enter email"
            binding.textInputPassword.error = "Please enter password"
        }
    }

}