package com.example.appv2.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClient {
    private const val SUPABASE_URL = "https://rqyytwpfcezjtndbjkwj.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_i-8NqtPKNyhcN5uo8UNyYA_T48QpeJ5"

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Auth) {
            flowType = FlowType.PKCE
            scheme = "app"
            host = "supabase.com"
        }
        install(Storage)
        install(Postgrest)
    }
}