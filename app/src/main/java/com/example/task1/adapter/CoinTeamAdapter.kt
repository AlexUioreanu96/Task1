package com.example.task1.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.databinding.ItemTeamBinding
import com.example.task1.models.CoinModel

class CoinTeamAdapter(var coinList: List<CoinModel>) :
    RecyclerView.Adapter<CoinTeamAdapter.CoinTeamViewHolder>() {
    data class CoinTeamViewHolder(val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinTeamViewHolder {

    }

    override fun onBindViewHolder(holder: CoinTeamViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}