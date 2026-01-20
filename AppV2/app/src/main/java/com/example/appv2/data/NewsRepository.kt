package com.example.appv2.data

import android.util.Log
import com.example.appv2.model.News
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

object NewsRepository {
    suspend fun getNews(): List<News> {
        return try {
            val response = SupabaseClient.client.from("news")
                .select {
                    order(column = "date", order = Order.DESCENDING)
                }
                .decodeList<News>()
            Log.d("NewsRepo", "Noticias recuperadas: ${response.size}")
            response
        } catch (e: Exception) {
            Log.e("NewsRepo", "Error: ${e.message}")
            emptyList()
        }
    }
}