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

/**
 * Adaptador para la lista de Equipos (en Results y TeamsActivity).
 * No recibe listener porque no hacemos nada al pulsar.
 */
class TeamAdapter : ListAdapter<Team, TeamAdapter.TeamViewHolder>(TeamDiff()) {

    /**
     * Crea la fila inflando item_team.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    /**
     * Conecta los datos de un Equipo con la fila.
     */
    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Clase interna que representa la fila.
     */
    inner class TeamViewHolder(private val b: ItemTeamBinding) : RecyclerView.ViewHolder(b.root) {
        /**
         * Conecta los datos del objeto 'team' (Team) con las Vistas del layout 'item_team.xml'.
         */
        fun bind(team: Team) {
            // Rellenamos los datos de los TextViews
            b.txtName.text = team.name
            b.txtPoints.text = "${team.points} PTS"

            // Cargamos las dos imágenes (Logo y Coche) con Coil (a través de Utils)
            Utils.loadImageInto(team.logoUrl, b.imgLogo, b.root.context)
            Utils.loadImageInto(team.carUrl, b.imgCar, b.root.context)
        }
    }

    /**
     * DiffUtil para el ListAdapter.
     */
    class TeamDiff : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team) = oldItem.teamId == newItem.teamId
        override fun areContentsTheSame(oldItem: Team, newItem: Team) = oldItem == newItem
    }
}