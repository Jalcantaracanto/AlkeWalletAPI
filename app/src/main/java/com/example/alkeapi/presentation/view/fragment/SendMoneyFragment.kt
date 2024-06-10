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
import com.example.alkeapi.data.local.database.AppDatabase
import com.example.alkeapi.data.local.model.Transaction
import com.example.alkeapi.data.local.model.User
import com.example.alkeapi.data.local.repository.TransactionR
import com.example.alkeapi.data.network.api.AlkeApiService
import com.example.alkeapi.data.network.retrofit.RetrofitHelper
import com.example.alkeapi.data.network.repository.AlkeRepositoryImplement
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.data.network.response.TransactionPost
import com.example.alkeapi.databinding.FragmentSendMoneyBinding
import com.example.alkeapi.domain.AlkeUseCase
import com.example.alkeapi.domain.DatabaseUseCase
import com.example.alkeapi.presentation.adapter.ContactAdapter
import com.example.alkeapi.presentation.viewmodel.sendmoney.SendMoneyViewModel
import com.example.alkeapi.presentation.viewmodel.sendmoney.SendMoneyViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SendMoneyFragment : Fragment() {

    private lateinit var binding: FragmentSendMoneyBinding
    private lateinit var sendMoneyViewModel: SendMoneyViewModel
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var validUsers: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeViewModel()
        setupSpinnerListener()

        binding.btnSendMoney.setOnClickListener {
            val selectedAccount = binding.spinnerSendMoney.selectedItem as AccountDataResponse
            val amountText = binding.txtAmount.editText?.text.toString()
            val concept = binding.txtConcept.text.toString()
            val UserSelected = validUsers.find { it.id == selectedAccount.userId }

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
                    account.id,
                    user.id,
                    selectedAccount.id
                )

                val userName = user.first_name + " " + user.last_name
                val userSelectedName = UserSelected?.first_name + " " + UserSelected?.last_name

                val newTransactionDataBase = Transaction(
                    sender_name = userSelectedName,
                    receiver_name = userName,
                    transacion_date = formattedDate,
                    isReceiver = false,
                    amount = amount.toString().toDouble(),
                    concept = concept
                )

                sendMoneyViewModel.insertTransaction(newTransactionDataBase)

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

        val database = AppDatabase.getDatabase(requireContext())
        val databaseRepository = TransactionR(database.transactionDao())
        val databaseUseCase = DatabaseUseCase(databaseRepository)

        val viewModelFactory = SendMoneyViewModelFactory(alkeUseCase, databaseUseCase)

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
                        binding.spinnerSendMoney.adapter = contactAdapter
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
        binding.spinnerSendMoney.onItemSelectedListener =
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