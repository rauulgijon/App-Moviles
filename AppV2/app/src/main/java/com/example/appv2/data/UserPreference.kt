package com.example.appv2.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    companion object {
        val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
    }

    val getNotifications: Flow<Boolean> = context.dataStore.data.map { it[NOTIFICATIONS_KEY] ?: true }
    val getDarkMode: Flow<Boolean> = context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    suspend fun saveNotifications(enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATIONS_KEY] = enabled }
    }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }
}