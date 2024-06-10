package com.example.alkeapi.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.alkeapi.R
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.databinding.TransactionItemBinding
import com.example.alkeapi.presentation.viewmodel.home.HomePageViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionAdapter(
    private val homePageViewModel: HomePageViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions: MutableList<TransactionDataResponse> = mutableListOf()

    init {
        homePageViewModel.transactions.observe(lifecycleOwner, Observer { newTransactions ->
            setTransactions(newTransactions)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val bindingItem =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(bindingItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun setTransactions(newTransactions: MutableList<TransactionDataResponse>) {
        transactions = newTransactions
        notifyDataSetChanged()
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (transactions.isEmpty()) {
            onTransactionsEmptyListener?.invoke(true)
        } else {
            onTransactionsEmptyListener?.invoke(false)
        }
    }

    var onTransactionsEmptyListener: ((Boolean) -> Unit)? = null

    inner class TransactionViewHolder(private val bindingItem: TransactionItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(transaction: TransactionDataResponse) {
            bindingItem.imageprofile.setImageResource(getRandomImageResource())
            bindingItem.txtfecha.text = formatDate(transaction.date)

            homePageViewModel.getAccountById(transaction.to_account_id)


            homePageViewModel.userAccount.observe(lifecycleOwner, Observer { account ->
                if (account != null && account.id == transaction.to_account_id) {
                    homePageViewModel.getUserById(account.userId)

                    homePageViewModel.userById.observe(
                        lifecycleOwner,
                        Observer { userTransactionList ->
                            val userTransaction = userTransactionList.find { it.id == account.userId }
                            userTransaction?.let {
                                bindingItem.txtnombrereceptor.text = it.first_name + " " + it.last_name
                            }

                            if (homePageViewModel.user.value?.id == account.userId) {
                                bindingItem.receiverarrow.visibility = View.VISIBLE
                                bindingItem.senderarrow.visibility = View.GONE
                                bindingItem.txtcantidad.text = "+$" + transaction.amount.toString()
                            } else {
                                bindingItem.receiverarrow.visibility = View.GONE
                                bindingItem.senderarrow.visibility = View.VISIBLE
                                bindingItem.txtcantidad.text = "-$" + transaction.amount.toString()
                            }
                        })
                }
            })
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

        @RequiresApi(Build.VERSION_CODES.O)
        private fun formatDate(dateString: String): String {
            val zonedDateTime =
                ZonedDateTime.parse(dateString, DateTimeFormatter.ISO_ZONED_DATE_TIME)
            val desiredFormat = DateTimeFormatter.ofPattern("MMM dd, hh:mm a", Locale.ENGLISH)
            return zonedDateTime.format(desiredFormat)
        }
    }
}