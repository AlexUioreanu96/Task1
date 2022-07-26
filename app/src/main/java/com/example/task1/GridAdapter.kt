package com.example.task1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.task1.databinding.ItemGridBinding

class GridAdapter(contexts: Context, private val list: List<String>) :
    ArrayAdapter<String>(contexts, 0, list) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.itemTxt.text = list[position]
        return binding.root
    }
}