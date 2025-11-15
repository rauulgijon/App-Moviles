package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels // Importar
import androidx.appcompat.app.AppCompatActivity
import com.example.appf1.R
import com.example.appf1.ScheduleActivity
import com.example.appf1.databinding.ActivityFantasyBinding
import model.Driver // Importar
import viewmodel.RaceViewModel

class FantasyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFantasyBinding
    // ViewModel para acceder a los pilotos
    private val vm: RaceViewModel by viewModels()

    // Lista para guardar los nombres de los pilotos
    private var driverNames = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFantasyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // --- ✅ CARGA DE DATOS DESDE EL VIEWMODEL ---

        // 1. Creamos un Adapter vacío
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mutableListOf<String>())
        binding.spinnerDriver.adapter = spinnerAdapter

        // 2. Observamos los pilotos de la BBDD
        vm.allDrivers.observe(this) { drivers ->
            // 3. Cuando los pilotos llegan, los procesamos
            if (drivers.isNotEmpty()) {
                // Convertimos la List<Driver> en una List<String>
                driverNames = drivers.map { it.name }

                // Limpiamos el adapter y añadimos los nuevos nombres
                spinnerAdapter.clear()
                spinnerAdapter.addAll(driverNames)
                spinnerAdapter.notifyDataSetChanged()
            }
        }

        // --- Lógica del botón ---
        binding.btnPredict.setOnClickListener {
            if (driverNames.isNotEmpty()) {
                val driver = binding.spinnerDriver.selectedItem as String
                val points = binding.seekBarPoints.progress

                Toast.makeText(this, getString(R.string.fantasy_saved, driver, points), Toast.LENGTH_SHORT).show()
            }
        }

        // --- LÓGICA DEL MENÚ ACTUALIZADA ---
        binding.bottomNav.selectedItemId = R.id.nav_fantasy // Marcar "Fantasy"
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
                R.id.nav_fantasy -> true // Ya estamos aquí
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