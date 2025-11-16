package com.example.appf1.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity // Importa AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.BaseActivity // Importamos nuestra BaseActivity
import com.example.appf1.R
import com.example.appf1.adapter.RaceAdapter
import com.example.appf1.databinding.ActivityScheduleBinding
import viewmodel.RaceViewModel

// Heredamos de BaseActivity para que el selector de idioma funcione
class ScheduleActivity : BaseActivity() {

    // ViewBinding para acceder a las vistas del XML (ej: binding.recyclerRaces)
    private lateinit var binding: ActivityScheduleBinding
    // ViewModel para obtener los datos de la base de datos
    private val raceViewModel: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar (crear) la vista usando ViewBinding
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        // Establecer esta vista como el contenido de la pantalla
        setContentView(binding.root)

        // Configurar la Toolbar (barra superior)
        setSupportActionBar(binding.topAppBar)

        // --- Lógica del RecyclerView (la lista de carreras) ---

        // 1. Creamos el adaptador. Le pasamos una función "lambda" (el código entre {})
        //    que se ejecutará CADA VEZ que el usuario pulse en una carrera.
        val adapter = RaceAdapter { race ->
            // 'race' es el objeto Race que el usuario ha pulsado.

            // 2. Cuando se pulsa, creamos un Intent para ir a GrandPrixDetailActivity
            val intent = Intent(this, GrandPrixDetailActivity::class.java)

            // 3. Añadimos el ID de la carrera pulsada como "extra"
            //    La pantalla de detalle usará este ID para saber qué carrera mostrar.
            intent.putExtra("RACE_ID", race.id)

            // 4. Iniciamos la nueva Activity (la de detalle)
            startActivity(intent)
        }

        // Asignamos el LayoutManager (vertical) y el adaptador a nuestro RecyclerView
        binding.recyclerRaces.layoutManager = LinearLayoutManager(this)
        binding.recyclerRaces.adapter = adapter

        // Observamos 'allRaces' (todas las carreras) del ViewModel
        raceViewModel.allRaces.observe(this) { races ->
            // Cuando la lista de carreras llega desde la BD, se la pasamos al adaptador.
            // El DAO ya las devuelve ordenadas por 'round' (ronda).
            adapter.submitList(races)
        }

        // --- Lógica del Menú Inferior ---

        // Marcamos el ítem "Calendario" como seleccionado
        binding.bottomNav.selectedItemId = R.id.nav_schedule

        // Añadimos el listener para saber qué ítem se ha pulsado
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Ir a Inicio (y borrar las otras pantallas de la pila)
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                    true
                }
                R.id.nav_schedule -> true // Ya estamos aquí, no hacer nada
                R.id.nav_results -> {
                    // Ir a Resultados (y cerrar esta)
                    startActivity(Intent(this, ResultsActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_fantasy -> {
                    // Ir a Fantasy (y cerrar esta)
                    startActivity(Intent(this, FantasyActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_settings -> {
                    // Ir a Ajustes (y cerrar esta)
                    startActivity(Intent(this, SettingsActivity::class.java))
                    finish()
                    true
                }
                else -> false // Opción desconocida
            }
        }
    }
}