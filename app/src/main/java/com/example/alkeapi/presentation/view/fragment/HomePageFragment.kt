package com.example.alkeapi.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alkeapi.R
import com.example.alkeapi.application.SharedPreferencesHelper
import com.example.alkeapi.data.local.database.AppDatabase
import com.example.alkeapi.data.local.repository.TransactionR
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.network.repository.AlkeRepositoryImplement
import com.example.alkeapi.databinding.FragmentHomePageBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.domain.DatabaseUseCase
import com.example.alkeapi.presentation.adapter.TestAdapter
import com.example.alkeapi.presentation.adapter.TransactionAdapter
import com.example.alkeapi.presentation.viewmodel.home.HomePageViewModel
import com.example.alkeapi.presentation.viewmodel.home.HomePageViewModelFactory


class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var testAdapter: TestAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val alkeRepository = AlkeRepositoryImplement(alkeApiService)
        val alkeUseCase = AlkeUseCase(alkeRepository)

        val database = AppDatabase.getDatabase(requireContext())
        val databaseRepository = TransactionR(database.transactionDao())
        val databaseUseCase = DatabaseUseCase(databaseRepository)
        val ViewModelFactory = HomePageViewModelFactory(alkeUseCase, databaseUseCase)

        homePageViewModel = ViewModelProvider(this, ViewModelFactory)[HomePageViewModel::class.java]
        val navController = Navigation.findNavController(view)

        binding.btnSendMoney.setOnClickListener { navController.navigate(R.id.sendMoneyFragment) }
        binding.btnRequestMoney.setOnClickListener { navController.navigate(R.id.requestMoneyFragment) }
        binding.imgUserProfile.setOnClickListener{ navController.navigate(R.id.profilePageFragment) }

        homePageViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.txtName.text = user.first_name
        }

        homePageViewModel.account.observe(viewLifecycleOwner) { account ->
            binding.txtBalance.text = "$" + account.money
        }

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
//        transactionAdapter = TransactionAdapter(homePageViewModel, viewLifecycleOwner)
//        binding.recyclerTransferencias.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerTransferencias.adapter = transactionAdapter
//
//        transactionAdapter.onTransactionsEmptyListener = { isEmpty ->
//            if (isEmpty) {
//                binding.recyclerTransferencias.visibility = View.GONE
//                binding.imgEmptyTransaction.visibility = View.VISIBLE
//                binding.txtEmptyTransaction.visibility = View.VISIBLE
//            } else {
//                binding.recyclerTransferencias.visibility = View.VISIBLE
//                binding.imgEmptyTransaction.visibility = View.GONE
//                binding.txtEmptyTransaction.visibility = View.GONE
//            }
//        }
        testAdapter = TestAdapter(homePageViewModel, viewLifecycleOwner)
        binding.recyclerTransferencias.adapter = testAdapter
        binding.recyclerTransferencias.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPreferencesHelper.clearUserData(requireContext())
        SharedPreferencesHelper.clearToken(requireContext())
    }

}