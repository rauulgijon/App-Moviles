package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.Driver
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class DriverRepository {
    suspend fun getDrivers(): List<Driver> {
        return try {
            SupabaseClient.client.from("drivers")
                .select {
                    order("points", Order.DESCENDING) // Ordenar por puntos
                }
                .decodeList<Driver>()
        } catch (e: Exception) {
            Log.e("DriverRepo", "Error: ${e.message}")
            emptyList()
        }
    }
}