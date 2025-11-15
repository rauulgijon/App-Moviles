package com.example.appf1.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appf1.R
import com.example.appf1.ScheduleActivity
import com.example.appf1.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        binding.switchNotifications.isChecked = prefs.getBoolean("notifications", true)
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
            Toast.makeText(this, getString(R.string.notifications_changed, isChecked), Toast.LENGTH_SHORT).show()
        }

        val langs = listOf(getString(R.string.lang_system), "Español", "English")
        binding.spinnerLanguage.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langs)

        binding.btnAbout.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }
        binding.btnSaveNickname.setOnClickListener {
            val nick = binding.etNickname.text.toString()
            prefs.edit().putString("nickname", nick).apply()
            Toast.makeText(this, getString(R.string.nickname_saved, nick), Toast.LENGTH_SHORT).show()
        }

        // --- ✅ LÓGICA DE BOTONES AÑADIDA ---
        binding.btnViewDrivers.setOnClickListener {
            startActivity(Intent(this, DriversActivity::class.java))
        }
        binding.btnViewTeams.setOnClickListener {
            startActivity(Intent(this, TeamsActivity::class.java))
        }

        // --- LÓGICA DEL MENÚ ACTUALIZADA ---
        binding.bottomNav.selectedItemId = R.id.nav_settings // Marcar "Settings"
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
                R.id.nav_settings -> true // Ya estamos aquí
                else -> false
            }
        }
    }
}