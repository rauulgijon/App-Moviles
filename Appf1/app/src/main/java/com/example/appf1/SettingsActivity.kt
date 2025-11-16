package com.example.appf1.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.appf1.BaseActivity // ✅ IMPORTANTE: Hereda de BaseActivity
import com.example.appf1.R
import com.example.appf1.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var prefs: SharedPreferences
    private val langValues by lazy { resources.getStringArray(R.array.language_values) }

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
            Toast.makeText(this, getString(R.string.notifications_changed, isChecked.toString()), Toast.LENGTH_SHORT).show()
        }

        setupLanguageSpinner()

        binding.btnAbout.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }
        binding.etNickname.setText(prefs.getString("nickname", "")) // Cargar apodo guardado
        binding.btnSaveNickname.setOnClickListener {
            val nick = binding.etNickname.text.toString()
            prefs.edit().putString("nickname", nick).apply()
            Toast.makeText(this, getString(R.string.nickname_saved, nick), Toast.LENGTH_SHORT).show()
        }

        binding.btnViewDrivers.setOnClickListener {
            startActivity(Intent(this, DriversActivity::class.java))
        }
        binding.btnViewTeams.setOnClickListener {
            startActivity(Intent(this, TeamsActivity::class.java))
        }

        binding.bottomNav.selectedItemId = R.id.nav_settings
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
                R.id.nav_settings -> true
                else -> false
            }
        }
    }

    private fun setupLanguageSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.language_entries, android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinnerLanguage.adapter = adapter

        val currentLang = prefs.getString("language", "system") ?: "system"
        val currentLangIndex = langValues.indexOf(currentLang)
        if (currentLangIndex != -1) {
            binding.spinnerLanguage.setSelection(currentLangIndex)
        }

        binding.spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLang = langValues[position]

                if (selectedLang != prefs.getString("language", "system")) {
                    prefs.edit().putString("language", selectedLang).apply()

                    val appLocale = if (selectedLang == "system") {
                        LocaleListCompat.getEmptyLocaleList()
                    } else {
                        LocaleListCompat.forLanguageTags(selectedLang)
                    }
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}