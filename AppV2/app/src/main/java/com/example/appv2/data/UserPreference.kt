package com.example.appv2.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension para crear el DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreference(private val context: Context) {
    // Definición de las claves para almacenar los datos
    companion object {
        val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }
    // LECTURA DE DATOS
    // Notificaciones
    val notificationsFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[NOTIFICATIONS_KEY] ?: true }
    // Nombre
    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USER_NAME_KEY] ?: "Usuario Invitado" }

    // Email
    val userEmailFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[USER_EMAIL_KEY] ?: "usuario@f1app.com" }

    // ESCRITURA DE DATOS
    suspend fun saveNotifications(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }
    suspend fun saveUserProfile(name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
            preferences[USER_EMAIL_KEY] = email
        }
    }
}