package com.example.task1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.databinding.ItemTeamBinding
import com.example.task1.models.TeamModel

class CoinTeamAdapter(var coinTeamList: List<TeamModel>) :
    RecyclerView.Adapter<CoinTeamAdapter.CoinTeamViewHolder>() {
    data class CoinTeamViewHolder(val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinTeamViewHolder {
        return CoinTeamViewHolder(
            ItemTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinTeamViewHolder, position: Int) {
        val coinTeam: TeamModel = coinTeamList[position]
        holder.binding.apply {
            teamMember.text = coinTeam.name
            positionItem.text = coinTeam.position
        }
    }

    override fun getItemCount(): Int = coinTeamList.size

}