package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.BaseActivity // Heredamos de BaseActivity
import com.example.appf1.R
import com.example.appf1.adapter.DriverAdapter
import com.example.appf1.adapter.RaceAdapter
import com.example.appf1.adapter.TeamAdapter
import com.example.appf1.databinding.ActivityResultsBinding
import com.google.android.material.tabs.TabLayout
import viewmodel.RaceViewModel

// Heredamos de BaseActivity para que el selector de idioma funcione
class ResultsActivity : BaseActivity() {

    // ViewBinding para las vistas
    private lateinit var binding: ActivityResultsBinding
    // ViewModel para los datos
    private val vm: RaceViewModel by viewModels()

    // Creamos una instancia de cada adaptador para los RecyclerViews
    // Para RaceAdapter, le pasamos una "lambda" (función) que se ejecutará al pulsar un ítem
    private val raceAdapter = RaceAdapter { race ->
        val i = Intent(this, GrandPrixDetailActivity::class.java)
        i.putExtra("RACE_ID", race.id) // Pasamos el ID a la pantalla de detalle
        startActivity(i)
    }
    private val driverAdapter = DriverAdapter()
    private val teamAdapter = TeamAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar) // Toolbar simple (sin flecha de volver)

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

    /**
     * Asigna a cada RecyclerView su LayoutManager (vertical) y su Adaptador.
     */
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

    /**
     * Se suscribe a los LiveData del ViewModel.
     * Cuando los datos cambien en la BD, este código se ejecutará.
     */
    private fun observeViewModel() {
        // Observar Carreras
        vm.allRaces.observe(this) { races ->
            // Filtramos la lista: solo queremos carreras que SÍ tengan un ganador (!= null)
            raceAdapter.submitList(races.filter { !it.winner.isNullOrEmpty() })
        }

        // Observar Pilotos
        vm.allDrivers.observe(this) { drivers ->
            // Le pasamos la lista completa (ya viene ordenada por puntos desde el DAO)
            driverAdapter.submitList(drivers)
        }

        // Observar Equipos
        vm.allTeams.observe(this) { teams ->
            // Le pasamos la lista completa (ordenada por puntos desde el DAO)
            teamAdapter.submitList(teams)
        }
    }

    /**
     * Añade las pestañas (Carreras, Pilotos, Equipos) y
     * define la lógica para mostrar/ocultar los RecyclerViews correctos.
     */
    private fun setupTabs() {
        // Añadir las pestañas manualmente
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Carreras"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pilotos"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Equipos"))

        // Añadir listener para cambiar la visibilidad de los RecyclerViews
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> { // Pestaña "Carreras"
                        binding.recyclerRaces.visibility = View.VISIBLE
                        binding.recyclerDrivers.visibility = View.GONE
                        binding.recyclerTeams.visibility = View.GONE
                    }
                    1 -> { // Pestaña "Pilotos"
                        binding.recyclerRaces.visibility = View.GONE
                        binding.recyclerDrivers.visibility = View.VISIBLE
                        binding.recyclerTeams.visibility = View.GONE
                    }
                    2 -> { // Pestaña "Equipos"
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