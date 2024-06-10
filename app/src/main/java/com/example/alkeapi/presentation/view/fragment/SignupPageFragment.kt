package com.example.alkeapi.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.alkeapi.R
import com.example.alkeapi.data.network.response.UserPost
import com.example.alkeapi.databinding.FragmentSignupPageBinding
import com.example.alkeapi.presentation.viewmodel.signup.SignupViewModel


class SignupPageFragment : Fragment() {

    private lateinit var binding: FragmentSignupPageBinding
    private lateinit var signupViewModel: SignupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateAccount.setOnClickListener{
            registerAccount()
        }

        val navController = Navigation.findNavController(view)
        binding.txtHaveAccount.setOnClickListener { navController.navigate(R.id.loginPageFragment) }
    }

    fun registerAccount() {
        val name = binding.txtName.editText?.text.toString()
        val lastname = binding.txtLastname.editText?.text.toString()
        val email = binding.txtEmail.editText?.text.toString()
        val password = binding.txtPassword.editText?.text.toString()
        val confirmPassword = binding.txtPasswordCheck.editText?.text.toString()

        var isValid = true

        if (name.isEmpty()) {
            binding.txtName.editText?.error = "Ingrese su nombre"
            isValid = false
        } else {
            binding.txtName.editText?.error = null
        }

        if (lastname.isEmpty()) {
            binding.txtLastname.editText?.error = "Ingrese su apellido"
            isValid = false
        } else {
            binding.txtLastname.editText?.error = null
        }

        if (email.isEmpty()) {
            binding.txtEmail.editText?.error = "Ingrese su email"
            isValid = false
        } else {
            binding.txtEmail.editText?.error = null
        }

        if (password.isEmpty()) {
            binding.txtPassword.editText?.error = "Ingrese su contraseña"
            isValid = false
        } else {
            binding.txtPassword.editText?.error = null
        }

        if (confirmPassword.isEmpty()) {
            binding.txtPasswordCheck.editText?.error = "Confirme su contraseña"
            isValid = false
        } else {
            binding.txtPasswordCheck.editText?.error = null
        }

        if (password != confirmPassword) {
            binding.txtPasswordCheck.editText?.error = "Las contraseñas no coinciden"
            isValid = false
        } else if (confirmPassword.isNotEmpty()) {
            binding.txtPasswordCheck.editText?.error = null
        }

        val newUser = UserPost(name, lastname, email, password)

        if (isValid) {
            signupViewModel.createUser(newUser)
        }
    }

}