package com.example.appf1.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.adapter.DriverAdapter
import com.example.appf1.databinding.ActivityDriversBinding
import viewmodel.RaceViewModel

class DriversActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDriversBinding
    private val vm: RaceViewModel by viewModels()
    private val adapter = DriverAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriversBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.recyclerViewDrivers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDrivers.adapter = adapter

        // Observamos los pilotos de la BBDD
        vm.allDrivers.observe(this) { drivers ->
            adapter.submitList(drivers)
        }
    }
}