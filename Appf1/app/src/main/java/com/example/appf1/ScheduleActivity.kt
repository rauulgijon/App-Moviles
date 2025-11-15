package com.example.appf1

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.activities.*
import com.example.appf1.adapter.RaceAdapter
import com.example.appf1.databinding.ActivityScheduleBinding
import viewmodel.RaceViewModel

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private val raceViewModel: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        // --- Lógica del RecyclerView (movida desde MainActivity) ---
        val adapter = RaceAdapter { race ->
            val intent = Intent(this, GrandPrixDetailActivity::class.java)
            intent.putExtra("RACE_ID", race.id)
            startActivity(intent)
        }

        binding.recyclerRaces.layoutManager = LinearLayoutManager(this)
        binding.recyclerRaces.adapter = adapter

        raceViewModel.allRaces.observe(this) { races ->
            adapter.submitList(races)
        }

        // --- Lógica del Menú Inferior ---
        binding.bottomNav.selectedItemId = R.id.nav_schedule // Marcar "Schedule"
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                    true
                }
                R.id.nav_schedule -> true // Ya estamos aquí
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultsActivity::class.java))
                    finish()
                    true
                }
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
}