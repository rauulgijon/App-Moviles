package com.example.appf1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ItemDriverBinding
import model.Driver

class DriverAdapter : ListAdapter<Driver, DriverAdapter.DriverViewHolder>(DriverDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val binding = ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DriverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DriverViewHolder(private val b: ItemDriverBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(driver: Driver) {
            b.txtName.text = driver.name
            b.txtTeam.text = driver.teamName
            b.txtNumber.text = driver.number.toString()
            Utils.loadImageInto(driver.imageUrl, b.imgDriver, b.root.context)

            // ✅ LÍNEA AÑADIDA (Formatea los puntos)
            b.txtPoints.text = "${driver.points} PTS"
        }
    }

    class DriverDiff : DiffUtil.ItemCallback<Driver>() {
        override fun areItemsTheSame(oldItem: Driver, newItem: Driver) = oldItem.driverId == newItem.driverId
        override fun areContentsTheSame(oldItem: Driver, newItem: Driver) = oldItem == newItem
    }
}