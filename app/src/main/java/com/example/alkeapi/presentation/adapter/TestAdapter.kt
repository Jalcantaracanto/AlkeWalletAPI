package com.example.alkeapi.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.alkeapi.R
import com.example.alkeapi.data.local.model.Transaction
import com.example.alkeapi.data.network.response.TransactionDataResponse
import com.example.alkeapi.databinding.TransactionItemBinding
import com.example.alkeapi.presentation.viewmodel.home.HomePageViewModel

class TestAdapter(
    private val homePageViewModel: HomePageViewModel, lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<TestAdapter.TestAdapterViewHolder>() {

    private var transactions: MutableList<Transaction> = mutableListOf()

    init {
        homePageViewModel.transactionsDatabase.observe(lifecycleOwner) { newTransactions ->
            transactions.clear()
            if (newTransactions != null) {
                transactions.addAll(newTransactions)
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestAdapter.TestAdapterViewHolder {
        val bindingItem =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestAdapterViewHolder(bindingItem)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: TestAdapter.TestAdapterViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    inner class TestAdapterViewHolder(private val bindingItem: TransactionItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(transaction: Transaction) {

            bindingItem.txtfecha.text = transaction.transacion_date

            if (transaction.isReceiver) {
                bindingItem.txtnombrereceptor.text = transaction.receiver_name
                bindingItem.receiverarrow.visibility = View.VISIBLE
                bindingItem.senderarrow.visibility = View.GONE
                bindingItem.txtcantidad.text = "+$" + transaction.amount.toString()
            } else {
                bindingItem.txtnombrereceptor.text = transaction.sender_name
                bindingItem.receiverarrow.visibility = View.GONE
                bindingItem.senderarrow.visibility = View.VISIBLE
                bindingItem.txtcantidad.text = "-$" + transaction.amount.toString()
            }
            bindingItem.imageprofile.setImageResource(getRandomImageResource())
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
}