package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.Race
import com.example.appv2.model.RaceResult
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

object RaceRepository {

    // Función existente para el calendario
    suspend fun getRaces(): List<Race> {
        return try {
            SupabaseClient.client.from("races")
                .select {
                    order(column = "round", order = Order.ASCENDING)
                }
                .decodeList<Race>()
        } catch (e: Exception) {
            Log.e("RaceRepository", "Error races: ${e.message}")
            emptyList()
        }
    }

    // NUEVA FUNCIÓN: Obtener resultados + datos del piloto
    suspend fun getRaceResults(raceId: Int): List<RaceResult> {
        return try {
            // Pedimos '*, driver:drivers(*)' para que Supabase nos incluya los datos del piloto dentro del resultado
            val results = SupabaseClient.client.from("race_results")
                .select(columns = Columns.raw("*, driver:drivers(*)")) {
                    filter {
                        eq("race_id", raceId)
                    }
                    order("position", order = Order.ASCENDING)
                }
                .decodeList<RaceResult>()

            Log.d("RaceRepository", "Resultados cargados: ${results.size}")
            results
        } catch (e: Exception) {
            Log.e("RaceRepository", "Error resultados: ${e.message}")
            emptyList()
        }
    }
}