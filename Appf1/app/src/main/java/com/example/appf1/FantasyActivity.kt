package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appf1.BaseActivity // ✅ IMPORTANTE: Hereda de BaseActivity
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ActivityFantasyBinding
import model.Driver
import viewmodel.RaceViewModel

// ✅ CAMBIO DE HERENCIA
class FantasyActivity : BaseActivity() {

    private lateinit var binding: ActivityFantasyBinding
    private val vm: RaceViewModel by viewModels()

    // Lista para guardar los pilotos COMPLETOS, no solo los nombres
    private var driverList = listOf<Driver>()
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
                driverList = drivers // Guardamos la lista completa
                driverNames = drivers.map { it.name } // Guardamos solo los nombres

                // Limpiamos el adapter y añadimos los nuevos nombres
                spinnerAdapter.clear()
                spinnerAdapter.addAll(driverNames)
                spinnerAdapter.notifyDataSetChanged()

                // Cargamos la imagen del primer piloto por defecto
                Utils.loadImageInto(driverList[0].imageUrl, binding.imgDriverFantasy, this)
            }
        }

        // --- ✅ LÓGICA DEL SPINNER PARA CAMBIAR IMAGEN ---
        binding.spinnerDriver.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (driverList.isNotEmpty()) {
                    val selectedDriver = driverList[position]
                    Utils.loadImageInto(selectedDriver.imageUrl, binding.imgDriverFantasy, this@FantasyActivity)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.imgDriverFantasy.setImageResource(R.drawable.ic_baseline_sports_motorsports_24)
            }
        }


        // --- Lógica del botón (sin cambios) ---
        binding.btnPredict.setOnClickListener {
            if (driverNames.isNotEmpty()) {
                val driver = binding.spinnerDriver.selectedItem as String
                val points = binding.seekBarPoints.progress

                Toast.makeText(this, getString(R.string.fantasy_saved, driver, points), Toast.LENGTH_SHORT).show()
            }
        }

        // --- LÓGICA DEL MENÚ (sin cambios) ---
        binding.bottomNav.selectedItemId = R.id.nav_fantasy
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