package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.R
import com.example.appf1.ScheduleActivity
import com.example.appf1.adapter.RaceAdapter
import com.example.appf1.databinding.ActivityResultsBinding
import viewmodel.RaceViewModel

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private val vm: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        val adapter = RaceAdapter { race ->
            val i = Intent(this, GrandPrixDetailActivity::class.java)
            i.putExtra("RACE_ID", race.id)
            startActivity(i)
        }
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewResults.adapter = adapter

        vm.allRaces.observe(this) { list ->
            // mostramos solo las finalizadas como "resultados"
            val finishedRaces = list.filter { it.winner != null }
            adapter.submitList(finishedRaces)
            binding.tvInfo.text = getString(R.string.results_info, finishedRaces.size)
        }

        binding.btnShowAll.setOnClickListener {
            vm.allRaces.value?.let { adapter.submitList(it) }
        }
        binding.btnShowFinished.setOnClickListener {
            vm.allRaces.value?.let { adapter.submitList(it.filter { r -> r.winner != null }) }
        }

        // --- LÓGICA DEL MENÚ ACTUALIZADA ---
        binding.bottomNav.selectedItemId = R.id.nav_results // Marcar "Resultados"
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity() // Cierra esta y vuelve a Home
                    true
                }
                R.id.nav_schedule -> {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    finish() // Cierra esta
                    true
                }
                R.id.nav_results -> true // Ya estamos aquí
                R.id.nav_fantasy -> {
                    startActivity(Intent(this, FantasyActivity::class.java))
                    finish() // Cierra esta
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish() // Cierra esta
                    true
                }
                else -> false
            }
        }
    }
}