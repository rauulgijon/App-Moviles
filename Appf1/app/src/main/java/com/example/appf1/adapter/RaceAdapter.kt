package com.example.appf1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ItemRaceBinding
import model.Race

class RaceAdapter(private val listener: (Race) -> Unit) :
    ListAdapter<Race, RaceAdapter.RaceViewHolder>(RaceDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val binding = ItemRaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class RaceViewHolder(private val b: ItemRaceBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(r: Race) {
            // Rellenamos los campos del nuevo layout
            b.txtRound.text = "ROUND ${r.round}"

            // Usamos el nombre del país (más corto) o el nombre completo
            b.txtRaceName.text = r.country ?: r.name.replace("Gran Premio de ", "")

            // Mostramos el nombre del circuito
            b.txtCircuitName.text = r.circuit

            b.txtRaceDate.text = r.date

            // Cargar imagen con Volley
            if (!r.imageUrl.isNullOrEmpty()) {
                Utils.loadImageInto(r.imageUrl, b.imgRace, b.root.context)
            } else {
                b.imgRace.setImageResource(R.drawable.ic_broken_image)
            }
            b.root.setOnClickListener { listener(r) }
        }
    }

    class RaceDiff : DiffUtil.ItemCallback<Race>() {
        override fun areItemsTheSame(oldItem: Race, newItem: Race) = oldItem.id == newItem.id

        // ✅ CORREGIDO: Faltaba "newItem: Race"
        override fun areContentsTheSame(oldItem: Race, newItem: Race) = oldItem == newItem
    }
}