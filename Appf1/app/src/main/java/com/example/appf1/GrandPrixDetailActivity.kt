package com.example.appf1.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appf1.BaseActivity
import com.example.appf1.R
// import com.example.appf1.ScheduleActivity // Ya no es necesario si moviste ScheduleActivity a 'activities'
import com.example.appf1.Utils
import com.example.appf1.databinding.ActivityGrandPrixDetailBinding
import viewmodel.RaceViewModel

class GrandPrixDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityGrandPrixDetailBinding
    private val vm: RaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGrandPrixDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de la Toolbar (sigue siendo necesaria)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }
        // ❌ ELIMINAMOS ESTA LÍNEA: supportActionBar?.title = "Detalles del GP"

        val raceId = intent.getIntExtra("RACE_ID", -1)
        if (raceId == -1) {
            finish()
            return
        }

        vm.getRaceById(raceId).observe(this) { race ->
            if (race == null) return@observe

            // ✅ AÑADIMOS EL TÍTULO A LA BARRA COLAPSABLE
            binding.collapsingToolbar.title = race.country ?: race.name

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

    }
}