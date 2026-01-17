package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.Team
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class TeamRepository {
    suspend fun getTeams(): List<Team> {
        return try {
            SupabaseClient.client.from("teams")
                .select {
                    order("points", Order.DESCENDING) // Ordenar por campeonato de constructores
                }
                .decodeList<Team>()
        } catch (e: Exception) {
            Log.e("TeamRepo", "Error: ${e.message}")
            emptyList()
        }
    }
}