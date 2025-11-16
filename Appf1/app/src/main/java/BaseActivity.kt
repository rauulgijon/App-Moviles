package com.example.appf1

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

/**
 * Esta es una clase "base" especial.
 * Todas nuestras Activities (MainActivity, SettingsActivity, etc.) heredarán de aquí.
 * Su único trabajo es configurar el idioma CADA VEZ que se abre una pantalla.
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * Esta función se llama ANTES de que la Activity se cree (antes de onCreate).
     * Es el lugar perfecto para configurar el idioma, ya que la UI aún no se ha "inflado".
     */
    override fun attachBaseContext(newBase: Context) {
        // 1. Leemos las SharedPreferences (nuestro archivo de ajustes guardados)
        // Usamos "app_prefs" que es el mismo nombre que usamos en SettingsActivity
        val prefs = newBase.getSharedPreferences("app_prefs", MODE_PRIVATE)

        // 2. Buscamos la preferencia "language". Si no existe, usamos "system" (idioma del teléfono)
        val lang = prefs.getString("language", "system") ?: "system"

        // 3. Aplicamos el idioma seleccionado
        if (lang != "system") {
            // Si el usuario eligió "es" (Español) o "en" (Inglés):
            // Creamos una lista de idiomas (Locale) para forzar ese idioma en la app.
            val appLocale = LocaleListCompat.forLanguageTags(lang)
            AppCompatDelegate.setApplicationLocales(appLocale)
        } else {
            // Si el usuario eligió "system":
            // Le pasamos una lista vacía, que le dice a Android que use el idioma por defecto del teléfono.
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
        }

        // 4. Una vez configurado el idioma, continuamos con la creación de la Activity.
        super.attachBaseContext(newBase)
    }
}