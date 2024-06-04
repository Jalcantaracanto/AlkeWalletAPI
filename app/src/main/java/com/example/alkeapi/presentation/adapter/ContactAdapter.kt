package com.example.alkeapi.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.alkeapi.R
import com.example.alkeapi.data.model.User
import com.example.alkeapi.databinding.ContactItemBinding

class ContactAdapter(context: Context, private val users: List<User>) :
    ArrayAdapter<User>(context, 0, users) {

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
        } else {
            binding = convertView.tag as ContactItemBinding
            view = convertView
        }

        val user = getItem(position)
        binding.txtNameContact.text = user?.first_name + " " + user?.last_name
        binding.txtEmailContact.text = user?.email
        binding.imgProfileContact.setImageResource(getRandomImageResource())

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