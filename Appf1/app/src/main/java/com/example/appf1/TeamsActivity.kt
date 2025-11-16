package com.example.appf1.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.BaseActivity // Heredamos de BaseActivity
import com.example.appf1.adapter.TeamAdapter
import com.example.appf1.databinding.ActivityTeamsBinding
import viewmodel.RaceViewModel

// Heredamos de BaseActivity para que el selector de idioma funcione
class TeamsActivity : BaseActivity() {

    private lateinit var binding: ActivityTeamsBinding
    private val vm: RaceViewModel by viewModels()
    // Creamos una instancia del adaptador
    private val adapter = TeamAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la Toolbar con la flecha de "volver"
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // Configura el RecyclerView
        binding.recyclerViewTeams.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTeams.adapter = adapter

        // Observamos los equipos de la BBDD
        vm.allTeams.observe(this) { teams ->
            // Cuando los datos llegan, se los pasamos al adaptador.
            // La lista ya viene ordenada por puntos desde el DAO.
            adapter.submitList(teams)
        }
    }
}