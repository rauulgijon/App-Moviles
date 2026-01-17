package com.example.appv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appv2.interfaz.screen.HomeScreen
import com.example.appv2.ui.theme.AppV2Theme // <--- EL IMPORT MÁGICO

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // APLICAMOS EL TEMA F1
            AppV2Theme {
                HomeScreen()
            }
        }
    }
}