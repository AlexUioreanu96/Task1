package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.databinding.ItemGridViewPagerBinding
import com.example.task1.models.MovieModel

class ViewPagerAdapter() :
    ListAdapter<MovieModel, ViewPagerAdapter.ExampleViewHolder>(object :
        DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel
        ) = oldItem == newItem
    }) {


    inner class ExampleViewHolder(private val binding: ItemGridViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: MovieModel) {
//            binding.title.text = model.title
            Glide.with(binding.root.context)
                .load(model.imageUrl)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view =
            ItemGridViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}