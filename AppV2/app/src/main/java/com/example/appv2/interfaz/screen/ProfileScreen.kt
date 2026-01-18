package com.example.appv2.interfaz.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appv2.R
import com.example.appv2.data.UserPreference
import com.example.appv2.interfaz.AboutActivity
import com.example.appv2.interfaz.EditProfileActivity
import com.example.appv2.interfaz.SettingsActivity

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val userPrefs = UserPreference(context)

    // Leemos los datos en tiempo real
    val userName by userPrefs.userNameFlow.collectAsState(initial = "Cargando...")
    val userEmail by userPrefs.userEmailFlow.collectAsState(initial = "")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Imagen de perfil
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Pic",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Datos del usuario (Dinámicos desde DataStore)
        Text(text = userName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = userEmail, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Editar Perfil
        Button(
            onClick = {
                context.startActivity(Intent(context, EditProfileActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.btn_edit_profile))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Ajustes
        OutlinedButton(
            onClick = {
                context.startActivity(Intent(context, SettingsActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Settings, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.menu_settings))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón Acerca De
        OutlinedButton(
            onClick = {
                context.startActivity(Intent(context, AboutActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.menu_about))
        }
    }
}