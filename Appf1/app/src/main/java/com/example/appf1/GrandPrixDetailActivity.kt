package com.example.appf1.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.appf1.BaseActivity // Heredamos de BaseActivity
import com.example.appf1.Utils
import com.example.appf1.databinding.ActivityGrandPrixDetailBinding
import viewmodel.RaceViewModel

// Heredamos de BaseActivity para que el selector de idioma funcione
class GrandPrixDetailActivity : BaseActivity() {

    // ViewBinding para las vistas (ej: binding.txtName)
    private lateinit var binding: ActivityGrandPrixDetailBinding
    // El ViewModel que nos da los datos
    private val vm: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrandPrixDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de la Toolbar (la que está DENTRO de la CollapsingToolbar)
        setSupportActionBar(binding.topAppBar)
        // Habilitamos la flecha de "Volver"
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // --- Obtener el ID de la carrera ---
        // Recibimos el ID que nos pasaron desde ScheduleActivity o MainActivity
        // Si no se pasa ningún ID, usamos -1 como valor por defecto.
        val raceId = intent.getIntExtra("RACE_ID", -1)
        if (raceId == -1) {
            // Si no hay ID, no podemos mostrar nada. Cerramos la Activity.
            finish()
            return
        }

        // --- Observar los datos ---
        // Pedimos al ViewModel la carrera con ese ID.
        // Esto es reactivo: si los datos cambian en la BD, la UI se actualiza sola.
        vm.getRaceById(raceId).observe(this) { race ->
            // El observador puede ser nulo si la carrera aún no ha cargado
            if (race == null) return@observe

            // --- Rellenar la UI con los datos de la carrera ---

            // Ponemos el título (ej: "Australia") en la barra colapsable
            binding.collapsingToolbar.title = race.country ?: race.name

            // Rellenamos los campos de texto
            binding.txtName.text = race.name
            binding.txtCircuit.text = race.circuit
            binding.txtDate.text = race.date
            binding.txtLocation.text = race.location
            binding.txtLaps.text = "${race.laps} vueltas"

            // Lógica para mostrar/ocultar la tarjeta de resultados
            if (race.winner != null) {
                // Si hay un ganador (la carrera ha pasado), mostramos la tarjeta
                binding.cardResults.visibility = View.VISIBLE
                binding.txtWinner.text = race.winner
                binding.txtTime.text = race.time
            } else {
                // Si no hay ganador (la carrera es futura), ocultamos la tarjeta
                binding.cardResults.visibility = View.GONE
            }

            // Cargar la imagen del circuito usando Coil (desde Utils.kt)
            if (!race.imageUrl.isNullOrEmpty()) {
                Utils.loadImageInto(race.imageUrl, binding.imgDetail, this)
            }

            // --- Lógica de Botones ---

            // Botón para abrir Google Maps
            binding.btnOpenMaps.setOnClickListener {
                // Codificamos el nombre del circuito para que la URL sea válida
                val etiqueta = Uri.encode(race.circuit)
                // Creamos un "Geo Intent" con la latitud y longitud de la BBDD
                val uri = Uri.parse("geo:0,0?q=${race.latitude},${race.longitude}($etiqueta)")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                // Le decimos que intente abrir la app de Google Maps
                intent.setPackage("com.google.android.apps.maps")

                if (intent.resolveActivity(packageManager) != null) {
                    // Si el usuario TIENE la app de Maps, la abre
                    startActivity(intent)
                } else {
                    // Si NO tiene la app de Maps, abre el navegador con la web de Maps
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://googleusercontent.com/maps?q=${race.latitude},${race.longitude}"))
                    startActivity(webIntent)
                }
            }
        }

    }
}