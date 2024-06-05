package com.example.alkeapi.presentation.view.fragment

import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.R
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.repository.AlkeRepositoryImplement
import com.example.alkeapi.databinding.FragmentProfilePageBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.presentation.viewmodel.HomePageViewModel
import com.example.alkeapi.presentation.viewmodel.HomePageViewModelFactory
import com.example.alkeapi.presentation.viewmodel.ProfilePageViewModel
import com.example.alkeapi.presentation.viewmodel.ProfilePageViewModelFactory
import com.example.alkeapi.presentation.viewmodel.SendMoneyViewModel

class ProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentProfilePageBinding
    private lateinit var profilePageViewModel: ProfilePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val alkeRepository = AlkeRepositoryImplement(alkeApiService)
        val alkeUseCase = AlkeUseCase(alkeRepository)
        val ViewModelFactory = ProfilePageViewModelFactory(alkeUseCase)

        profilePageViewModel = ViewModelProvider(this, ViewModelFactory)[ProfilePageViewModel::class.java]

        profilePageViewModel.user.observe(viewLifecycleOwner) {
            binding.txtProfileName.text = it.first_name + " " + it.last_name
        }
        binding.imageProfile.setImageResource(getRandomImageResource())

    }

    private fun getRandomImageResource(): Int {
        val images = listOf(
            R.drawable.pp1,
            R.drawable.pp2,
            R.drawable.pp3,
            R.drawable.pp4,
            R.drawable.pp5
        )
        return images.random()
    }

}