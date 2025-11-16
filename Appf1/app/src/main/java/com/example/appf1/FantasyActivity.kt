package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.appf1.BaseActivity // Heredamos de BaseActivity
import com.example.appf1.R
import com.example.appf1.Utils
import com.example.appf1.databinding.ActivityFantasyBinding
import model.Driver
import viewmodel.RaceViewModel

// Heredamos de BaseActivity para que el selector de idioma funcione
class FantasyActivity : BaseActivity() {

    private lateinit var binding: ActivityFantasyBinding
    // ViewModel para acceder a los pilotos
    private val vm: RaceViewModel by viewModels()

    // Lista para guardar los objetos Driver COMPLETOS
    private var driverList = listOf<Driver>()
    // Lista para guardar SÓLO los nombres (para el Spinner)
    private var driverNames = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFantasyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // --- Carga de datos desde el ViewModel ---

        // 1. Creamos un Adapter vacío para el Spinner
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mutableListOf<String>())
        binding.spinnerDriver.adapter = spinnerAdapter

        // 2. Observamos los pilotos de la BBDD (ya vienen ordenados por puntos)
        vm.allDrivers.observe(this) { drivers ->
            // 3. Cuando los pilotos llegan, los procesamos
            if (drivers.isNotEmpty()) {
                driverList = drivers // Guardamos la lista completa de objetos Driver
                // Creamos una lista de Nombres + Puntos (ej: "Max Verstappen (430 PTS)")
                driverNames = drivers.map { "${it.name} (${it.points} PTS)" }

                // Limpiamos el adapter y añadimos los nuevos nombres
                spinnerAdapter.clear()
                spinnerAdapter.addAll(driverNames)
                spinnerAdapter.notifyDataSetChanged()

                // Cargamos la imagen del primer piloto (el 1º en la clasificación) por defecto
                Utils.loadImageInto(driverList[0].imageUrl, binding.imgDriverFantasy, this)
            }
        }

        // --- Lógica del Spinner para cambiar la imagen ---
        binding.spinnerDriver.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Cuando el usuario selecciona un item (ej: en la posición 3)...
                if (driverList.isNotEmpty()) {
                    // Obtenemos el objeto Driver de esa posición
                    val selectedDriver = driverList[position]
                    // Usamos Utils para cargar la foto de ese piloto en el ImageView
                    Utils.loadImageInto(selectedDriver.imageUrl, binding.imgDriverFantasy, this@FantasyActivity)
                }
            }
            // Si no hay nada seleccionado (raro, pero hay que ponerlo)
            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.imgDriverFantasy.setImageResource(R.drawable.ic_baseline_sports_motorsports_24)
            }
        }


        // --- Lógica del botón de Guardar Predicción ---
        binding.btnPredict.setOnClickListener {
            if (driverList.isNotEmpty()) {
                // Obtenemos el nombre real del piloto (sin los puntos)
                val driverName = driverList[binding.spinnerDriver.selectedItemPosition].name
                // Obtenemos el valor del SeekBar
                val points = binding.seekBarPoints.progress

                // Mostramos un Toast con la confirmación (usando el string de strings.xml)
                Toast.makeText(this, getString(R.string.fantasy_saved, driverName, points), Toast.LENGTH_SHORT).show()
            }
        }

        // --- LÓGICA DEL MENÚ INFERIOR ---
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