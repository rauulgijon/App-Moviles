package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.Race
import com.example.appv2.model.RaceResult
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class RaceRepository {

    suspend fun getRaces(): List<Race> {
        return try {
            SupabaseClient.client.from("races")
                .select { order("id", Order.ASCENDING) }
                .decodeList<Race>()
        } catch (e: Exception) {
            Log.e("RaceRepo", "Error: ${e.message}")
            emptyList()
        }
    }

    suspend fun getRaceResults(raceId: Int): List<RaceResult> {
        return try {
            SupabaseClient.client.from("race_results")
                .select {
                    filter { eq("race_id", raceId) }
                    order("position", Order.ASCENDING)
                }
                .decodeList<RaceResult>()
        } catch (e: Exception) {
            Log.e("RaceRepo", "Error resultados: ${e.message}")
            emptyList()
        }
    }
}