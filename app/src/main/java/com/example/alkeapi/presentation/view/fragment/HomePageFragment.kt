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
import com.example.alkeapi.databinding.FragmentHomePageBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.presentation.viewmodel.HomePageViewModel
import com.example.alkeapi.presentation.viewmodel.HomePageViewModelFactory
import com.example.alkeapi.presentation.viewmodel.LoginViewModelFactory


class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val alkeRepository = AlkeRepositoryImplement(alkeApiService, context)
        val alkeUseCase = AlkeUseCase(alkeRepository)
        val ViewModelFactory = HomePageViewModelFactory(alkeUseCase, context)

        homePageViewModel = ViewModelProvider(this, ViewModelFactory)[HomePageViewModel::class.java]

        homePageViewModel.myProfile()
        homePageViewModel.myAccount()

        homePageViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.txtName.text = user.first_name
        }

        homePageViewModel.account.observe(viewLifecycleOwner) { account ->
            binding.txtBalance.text = "$ " + account.money
        }

    }


}