package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.News
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

class NewsRepository {
    suspend fun getNews(): List<News> {
        return try {
            SupabaseClient.client.from("news")
                .select { order("id", Order.DESCENDING) }
                .decodeList<News>()
        } catch (e: Exception) {
            Log.e("NewsRepo", "Error: ${e.message}")
            emptyList()
        }
    }
}