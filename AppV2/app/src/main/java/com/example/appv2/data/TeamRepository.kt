package com.example.appv2.data

import com.example.appv2.model.Team
import io.github.jan.supabase.postgrest.from

object TeamRepository {
    suspend fun getTeams(): List<Team> {
        return try {
            SupabaseClient.client.from("v_team_standings")
                .select()
                .decodeList<Team>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}