package com.example.appv2.data

import android.util.Log
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class AuthenticationRepository {

    suspend fun signIn(email: String, password: String): Boolean {
        return try {
            // Limpiamos sesión previa por seguridad
            SupabaseClient.client.auth.clearSession()

            SupabaseClient.client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            true // Login exitoso
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error SignIn: ${e.message}")
            false // Fallo
        }
    }

    suspend fun signUp(email: String, password: String): Boolean {
        return try {
            SupabaseClient.client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            true // Registro exitoso
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error SignUp: ${e.message}")
            false
        }
    }

    suspend fun signOut() {
        try {
            SupabaseClient.client.auth.signOut()
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error SignOut: ${e.message}")
        }
    }
}