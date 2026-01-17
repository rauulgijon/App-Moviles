package com.example.appv2.data

import com.example.appv2.model.Race
import com.example.appv2.model.RaceResult
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RaceRepository {

    // Obtener todas las carreras (para la lista principal)
    suspend fun getRaces(): List<Race> {
        return withContext(Dispatchers.IO) {
            try {
                // Ordenamos por ronda (round) para que salgan en orden
                SupabaseClient.client.from("races")
                    .select().decodeList<Race>()
                    .sortedBy { it.round }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    // Obtener los resultados de UNA carrera específica
    suspend fun getRaceResults(raceId: Int): List<RaceResult> {
        return withContext(Dispatchers.IO) {
            try {
                // Filtramos por 'race_id' y ordenamos por 'position'
                SupabaseClient.client.from("race_results")
                    .select {
                        filter {
                            eq("race_id", raceId)
                        }
                    }.decodeList<RaceResult>()
                    .sortedBy { it.position }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}