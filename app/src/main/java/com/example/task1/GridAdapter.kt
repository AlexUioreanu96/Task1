package com.example.task1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.task1.databinding.ItemGridBinding

class GridAdapter(private val contexts: Context, private val list: List<String>) :
    ArrayAdapter<String>(contexts, 0, list) {

    lateinit var binding: ItemGridBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        binding = ItemGridBinding.inflate(LayoutInflater.from(context), parent, false)

        return binding.root
    }
}