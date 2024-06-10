package com.example.alkeapi.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.alkeapi.R
import com.example.alkeapi.data.local.model.User
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkeapi.databinding.ContactItemBinding

class ContactAdapter(
    context: Context,
    private val accounts: List<AccountDataResponse>,
    private val users: List<User>
) : ArrayAdapter<AccountDataResponse>(context, 0, accounts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ContactItemBinding
        val view: View

        if (convertView == null) {
            binding = ContactItemBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding

            // Establecer la imagen solo una vez cuando se crea la vista por primera vez
            binding.imgProfileContact.setImageResource(getRandomImageResource())
        } else {
            binding = convertView.tag as ContactItemBinding
            view = convertView
        }

        val account = getItem(position)
        val user = users.find { it.id == account?.userId }

        if (user != null && user.first_name != null) {
            binding.txtNameContact.text = user.first_name + " " + user.last_name
            binding.txtEmailContact.text = user.email
        } else {
            binding.txtNameContact.text = "Unknown User"
            binding.txtEmailContact.text = "Unknown Email"
        }

        return view
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