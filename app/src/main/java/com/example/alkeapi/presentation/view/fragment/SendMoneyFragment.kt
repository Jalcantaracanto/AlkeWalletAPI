package com.example.alkeapi.presentation.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alkeapi.R
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.repository.AlkeRepositoryImplement
import com.example.alkeapi.data.response.UserDataResponse
import com.example.alkeapi.data.response.UserResponse
import com.example.alkeapi.databinding.FragmentSendMoneyBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.presentation.adapter.ContactAdapter
import com.example.alkeapi.presentation.adapter.TransactionAdapter
import com.example.alkeapi.presentation.viewmodel.HomePageViewModel
import com.example.alkeapi.presentation.viewmodel.HomePageViewModelFactory
import com.example.alkeapi.presentation.viewmodel.SendMoneyViewModel
import com.example.alkeapi.presentation.viewmodel.SendMoneyViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SendMoneyFragment : Fragment() {

    private lateinit var binding : FragmentSendMoneyBinding
    private lateinit var sendMoneyViewModel: SendMoneyViewModel
    private lateinit var contactAdapter : ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendMoneyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val alkeRepository = AlkeRepositoryImplement(alkeApiService)
        val alkeUseCase = AlkeUseCase(alkeRepository)
        val viewModelFactory = SendMoneyViewModelFactory(alkeUseCase)

        sendMoneyViewModel = ViewModelProvider(this, viewModelFactory)[SendMoneyViewModel::class.java]

        sendMoneyViewModel.usersResult.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                contactAdapter = ContactAdapter(context, it)
                binding.spinnerSendMoney.adapter = contactAdapter
            }
        })
        binding.spinnerSendMoney.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedUser = parent?.getItemAtPosition(position) as User

                Toast.makeText(context, "Seleccionado: ${selectedUser.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó ningún usuario
            }
        }



    }


}