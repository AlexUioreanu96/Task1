package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.databinding.ItemPopularPeopleBinding
import com.example.task1.models.Star

class PopularPeopleAdapter() :
    RecyclerView.Adapter<PopularPeopleAdapter.PopluarPeopleViewHolder>() {

    var list = listOf<Star>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    data class PopluarPeopleViewHolder(val binding: ItemPopularPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(star: Star) {
            val photo = "https://image.tmdb.org/t/p/w500${star.profilePath}"
            Glide.with(binding.root.context)
                .load(photo)
                .circleCrop()
                .into(binding.imgPopular)
            binding.txtPopular.text = star.name
            binding.txtPopular.isSelected = true
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopluarPeopleViewHolder {
        return PopularPeopleAdapter.PopluarPeopleViewHolder(
            ItemPopularPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: PopluarPeopleViewHolder, position: Int) {
        holder.bind((list[position]))
    }

    override fun getItemCount(): Int = list.size

}