package com.example.appf1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ItemTeamBinding
import model.Team

class TeamAdapter : ListAdapter<Team, TeamAdapter.TeamViewHolder>(TeamDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeamViewHolder(private val b: ItemTeamBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(team: Team) {
            b.txtName.text = team.name
            // Cargamos logo y coche
            Utils.loadImageInto(team.logoUrl, b.imgLogo, b.root.context)
            Utils.loadImageInto(team.carUrl, b.imgCar, b.root.context)
        }
    }

    class TeamDiff : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team) = oldItem.teamId == newItem.teamId
        override fun areContentsTheSame(oldItem: Team, newItem: Team) = oldItem == newItem
    }
}