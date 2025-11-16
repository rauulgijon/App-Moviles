package com.example.appf1.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.BaseActivity // Heredamos de BaseActivity
import com.example.appf1.adapter.DriverAdapter
import com.example.appf1.databinding.ActivityDriversBinding
import viewmodel.RaceViewModel

// Heredamos de BaseActivity para que el selector de idioma funcione
class DriversActivity : BaseActivity() {

    private lateinit var binding: ActivityDriversBinding
    private val vm: RaceViewModel by viewModels()
    // Creamos una instancia del adaptador para la lista
    private val adapter = DriverAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriversBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la Toolbar con la flecha de "volver"
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // Configura el RecyclerView
        binding.recyclerViewDrivers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDrivers.adapter = adapter

        // Observamos los pilotos de la BBDD
        vm.allDrivers.observe(this) { drivers ->
            // Cuando los datos llegan, se los pasamos al adaptador.
            // La lista ya viene ordenada por puntos desde el DAO.
            adapter.submitList(drivers)
        }
    }
}