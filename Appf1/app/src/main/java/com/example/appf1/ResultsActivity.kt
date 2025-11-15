package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.R
// import com.example.appf1.ScheduleActivity // Ya no es necesario si moviste ScheduleActivity a 'activities'
import com.example.appf1.adapter.DriverAdapter
import com.example.appf1.adapter.RaceAdapter
import com.example.appf1.adapter.TeamAdapter
import com.example.appf1.databinding.ActivityResultsBinding
import com.google.android.material.tabs.TabLayout
import viewmodel.RaceViewModel

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding
    private val vm: RaceViewModel by viewModels()

    // Creamos los 3 adaptadores
    private val raceAdapter = RaceAdapter { race ->
        val i = Intent(this, GrandPrixDetailActivity::class.java)
        i.putExtra("RACE_ID", race.id)
        startActivity(i)
    }
    private val driverAdapter = DriverAdapter()
    private val teamAdapter = TeamAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        // 1. Configurar los 3 RecyclerViews
        setupRecyclerViews()

        // 2. Observar los datos del ViewModel
        observeViewModel()

        // 3. Configurar las Pestañas (Tabs)
        setupTabs()

        // 4. Lógica del Menú Inferior
        binding.bottomNav.selectedItemId = R.id.nav_results // Marcar "Resultados"
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                    true
                }
                R.id.nav_schedule -> {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_results -> true // Ya estamos aquí
                R.id.nav_fantasy -> {
                    startActivity(Intent(this, FantasyActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerViews() {
        // Configurar RecyclerView de Carreras
        binding.recyclerRaces.layoutManager = LinearLayoutManager(this)
        binding.recyclerRaces.adapter = raceAdapter

        // Configurar RecyclerView de Pilotos
        binding.recyclerDrivers.layoutManager = LinearLayoutManager(this)
        binding.recyclerDrivers.adapter = driverAdapter

        // Configurar RecyclerView de Equipos
        binding.recyclerTeams.layoutManager = LinearLayoutManager(this)
        binding.recyclerTeams.adapter = teamAdapter
    }

    private fun observeViewModel() {
        // Observar Carreras (solo las que tienen ganador)
        vm.allRaces.observe(this) { races ->
            raceAdapter.submitList(races.filter { !it.winner.isNullOrEmpty() })
        }

        // Observar Pilotos
        vm.allDrivers.observe(this) { drivers ->
            driverAdapter.submitList(drivers)
        }

        // Observar Equipos
        vm.allTeams.observe(this) { teams ->
            teamAdapter.submitList(teams)
        }
    }

    private fun setupTabs() {
        // Añadir las pestañas manualmente
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Carreras"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pilotos"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Equipos"))

        // Añadir listener para cambiar la visibilidad de los RecyclerViews
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> { // Carreras
                        binding.recyclerRaces.visibility = View.VISIBLE
                        binding.recyclerDrivers.visibility = View.GONE
                        binding.recyclerTeams.visibility = View.GONE
                    }
                    1 -> { // Pilotos
                        binding.recyclerRaces.visibility = View.GONE
                        binding.recyclerDrivers.visibility = View.VISIBLE
                        binding.recyclerTeams.visibility = View.GONE
                    }
                    2 -> { // Equipos
                        binding.recyclerRaces.visibility = View.GONE
                        binding.recyclerDrivers.visibility = View.GONE
                        binding.recyclerTeams.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}