package com.example.appv2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.appv2.data.SupabaseClient
import com.example.appv2.interfaz.LoginActivity
import com.example.appv2.interfaz.screen.HomeScreen
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Aquí llamamos a la pantalla con los menús
                HomeScreen(
                    onLogout = {
                        // Lógica para cerrar sesión
                        CoroutineScope(Dispatchers.IO).launch {
                            SupabaseClient.client.auth.signOut()
                        }
                        // Volver al Login y cerrar esta pantalla
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}