package com.example.appf1.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appf1.R
import com.example.appf1.ScheduleActivity // Importar
import com.example.appf1.Utils
import com.example.appf1.databinding.ActivityGrandPrixDetailBinding
import viewmodel.RaceViewModel

class GrandPrixDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGrandPrixDetailBinding
    private val vm: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrandPrixDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }
        supportActionBar?.title = "Detalles del GP"

        val raceId = intent.getIntExtra("RACE_ID", -1)
        if (raceId == -1) {
            finish()
            return
        }

        vm.getRaceById(raceId).observe(this) { race ->
            if (race == null) return@observe

            binding.txtName.text = race.name
            binding.txtCircuit.text = race.circuit
            binding.txtDate.text = race.date
            binding.txtLocation.text = race.location
            binding.txtLaps.text = "${race.laps} vueltas"

            // ✅ Lógica para mostrar Ganador y Tiempo SOLO si existen
            if (race.winner != null) {
                binding.cardResults.visibility = View.VISIBLE
                binding.txtWinner.text = race.winner
                binding.txtTime.text = race.time
            } else {
                binding.cardResults.visibility = View.GONE
            }

            if (!race.imageUrl.isNullOrEmpty()) {
                Utils.loadImageInto(race.imageUrl, binding.imgDetail, this)
            }

            binding.btnOpenMaps.setOnClickListener {
                val etiqueta = Uri.encode(race.circuit)
                val uri = Uri.parse("geo:0,0?q=${race.latitude},${race.longitude}($etiqueta)")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://googleusercontent.com/maps?q=${race.latitude},${race.longitude}"))
                    startActivity(webIntent)
                }
            }
        }

        // --- Menú inferior (Actualizado) ---
        binding.bottomNav.menu.setGroupCheckable(0, false, true)
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