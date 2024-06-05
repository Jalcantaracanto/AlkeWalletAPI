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
import androidx.navigation.fragment.findNavController
import com.example.alkeapi.R
import com.example.alkeapi.data.model.User
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.repository.AlkeRepositoryImplement
import com.example.alkeapi.data.response.AccountDataResponse
import com.example.alkeapi.data.response.TransactionPost
import com.example.alkeapi.databinding.FragmentRequestMoneyBinding
import com.example.alkeapi.databinding.FragmentSendMoneyBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.presentation.adapter.ContactAdapter
import com.example.alkeapi.presentation.viewmodel.SendMoneyViewModel
import com.example.alkeapi.presentation.viewmodel.SendMoneyViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RequestMoneyFragment : Fragment() {

    private lateinit var binding: FragmentRequestMoneyBinding
    private lateinit var sendMoneyViewModel: SendMoneyViewModel
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var validUsers: List<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupSpinnerListener()

        binding.btnRequestMoney.setOnClickListener {
            val selectedAccount = binding.spinnerRequestMoney.selectedItem as AccountDataResponse
            val amountText = binding.txtAmountRequest.editText?.text.toString()
            val concept = binding.txtConceptRequest.text.toString()

            if (amountText.isEmpty() || concept.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val amount = amountText.toInt()
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDate = dateFormat.format(date)

            val account = sendMoneyViewModel.account.value
            val user = sendMoneyViewModel.user.value

            if (account != null && user != null) {
                val newTransaction = TransactionPost(
                    amount,
                    concept,
                    formattedDate,
                    type = "transfer",
                    selectedAccount.id,
                    user.id,
                    account.id
                )

                sendMoneyViewModel.createTransaction(newTransaction)
                Toast.makeText(
                    requireContext(),
                    "Transaccion creada con exito",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.homePageFragment)

            } else {
                Toast.makeText(
                    requireContext(),
                    "Account or User data is missing",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupViewModel() {
        val context = requireContext()
        val alkeApiService = RetrofitHelper.getRetrofit(context).create(AlkeApiService::class.java)
        val alkeRepository = AlkeRepositoryImplement(alkeApiService)
        val alkeUseCase = AlkeUseCase(alkeRepository)
        val viewModelFactory = SendMoneyViewModelFactory(alkeUseCase)

        sendMoneyViewModel =
            ViewModelProvider(this, viewModelFactory)[SendMoneyViewModel::class.java]
    }

    private fun observeViewModel() {
        sendMoneyViewModel.accountResult.observe(viewLifecycleOwner, Observer { accounts ->
            Log.d("SendMoneyFragment", "Accounts: $accounts")
            accounts?.let {
                sendMoneyViewModel.usersResult.observe(viewLifecycleOwner, Observer { users ->
                    Log.d("SendMoneyFragment", "Users: $users")
                    users?.let {
                        validUsers = users.filter { it.first_name != null }
                        contactAdapter = ContactAdapter(requireContext(), accounts, validUsers)
                        binding.spinnerRequestMoney.adapter = contactAdapter
                    }
                })
            }
        })

        sendMoneyViewModel.transactionResult.observe(
            viewLifecycleOwner,
            Observer { transactionSuccess ->
                if (transactionSuccess) {
                    Toast.makeText(requireContext(), "Transacción exitosa", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Transacción fallida", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun setupSpinnerListener() {
        binding.spinnerRequestMoney.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedAccount = parent?.getItemAtPosition(position) as AccountDataResponse
                    val selectedUser = validUsers.find { it.id == selectedAccount.userId }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // No user selected
                }
            }
    }

}