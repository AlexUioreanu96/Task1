package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.databinding.ItemMoviesBinding
import com.example.task1.models.Star

class MoviesAdapter() :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    var list = listOf<Star>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    data class MoviesViewHolder(val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(star: Star) {
            val photo = "https://image.tmdb.org/t/p/w500${star.profilePath}"
            Glide.with(binding.root.context)
                .load(photo)
                .circleCrop()
                .into(binding.imgTopRated)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        return MoviesAdapter.MoviesViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind((list[position]))
    }

    override fun getItemCount(): Int = list.size

}