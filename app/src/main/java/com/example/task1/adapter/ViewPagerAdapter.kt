package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task1.R
import com.example.task1.databinding.ItemGridViewPagerBinding
import com.example.task1.models.ImagesModel
import java.time.LocalDateTime

class ViewPagerAdapter() :
    ListAdapter<ImagesModel, ViewPagerAdapter.PagerViewHolder>(object :
        DiffUtil.ItemCallback<ImagesModel>() {
        override fun areItemsTheSame(
            oldItem: ImagesModel,
            newItem: ImagesModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ImagesModel,
            newItem: ImagesModel
        ) = oldItem == newItem
    }) {


    inner class PagerViewHolder(private val binding: ItemGridViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(model: ImagesModel) {
            Glide.with(binding.root.context)
                .load(model.imageUrl)
                .into(binding.image)
            if (model.releaseDate > LocalDateTime.now().toString()) binding.textView2.text =
                R.string.Coming_soon.toString() else binding.textView2.text =
                R.string.Out_in_cinemas.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view =
            ItemGridViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        try {
            holder.binding(getItem(position))
        } catch (e: Exception) {
        }

    }
}