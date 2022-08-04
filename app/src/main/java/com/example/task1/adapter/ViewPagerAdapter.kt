package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.databinding.ItemGridViewPagerBinding
import com.example.task1.models.Images
import java.time.LocalDateTime

class ViewPagerAdapter() :
    ListAdapter<Images, ViewPagerAdapter.PagerViewHolder>(object :
        DiffUtil.ItemCallback<Images>() {
        override fun areItemsTheSame(
            oldItem: Images,
            newItem: Images
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Images,
            newItem: Images
        ) = oldItem == newItem
    }) {


    inner class PagerViewHolder(private val binding: ItemGridViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: Images) {
            Glide.with(binding.root.context)
                .load(model.imageUrl)
                .into(binding.image)
            if (model.releaseDate > LocalDateTime.now().toString()) binding.textView2.text =
                "Coming soon" else binding.textView2.text = "Out in cinemas"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view =
            ItemGridViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}