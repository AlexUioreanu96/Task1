package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.CountriesQuery
import com.example.task1.databinding.ItemCountryBinding

class CountriesAdapter() :
    RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    var list = listOf<CountriesQuery.Country>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    data class CountriesViewHolder(val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: CountriesQuery.Country) {
            binding.imgFav.visibility = View.GONE

            binding.imgCountry.text = country.emoji
            binding.txtCountryName.text = country.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountriesViewHolder {
        return CountriesViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}