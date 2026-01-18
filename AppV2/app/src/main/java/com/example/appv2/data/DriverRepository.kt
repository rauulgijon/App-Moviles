package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.Driver
import io.github.jan.supabase.postgrest.from

object DriverRepository {
    suspend fun getDrivers(): List<Driver> {
        return try {
            // Leemos de la VISTA calculada en SQL
            SupabaseClient.client.from("v_driver_standings")
                .select()
                .decodeList<Driver>()
        } catch (e: Exception) {
            Log.e("REPO", "Error drivers: ${e.message}")
            emptyList()
        }
    }
}