package com.example.appf1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/**
 * Esta Activity base se encarga de aplicar el idioma guardado en SharedPreferences
 * CADA VEZ que se abre una Activity.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        // Lee el idioma guardado en "app_prefs"
        val prefs = newBase.getSharedPreferences("app_prefs", MODE_PRIVATE)
        val lang = prefs.getString("language", "system") ?: "system"

        // Aplica el idioma ANTES de que la Activity se cree
        if (lang != "system") {
            val appLocale = LocaleListCompat.forLanguageTags(lang)
            AppCompatDelegate.setApplicationLocales(appLocale)
        } else {
            // Usa el idioma del sistema
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
        }

        super.attachBaseContext(newBase)
    }
}