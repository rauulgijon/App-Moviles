package com.example.appv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Importante para lanzar corrutinas
import com.example.appv2.data.AuthenticationRepository
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    // Instancia del repositorio
    private val repository = AuthenticationRepository()

    // Las funciones suspendidas se deben llamar desde una corrutina o devolver el resultado
    // Aquí las mantenemos suspend para llamarlas desde el scope de la UI (LaunchedEffect o rememberCoroutineScope)

    suspend fun onSignUp(email: String, password: String): Boolean {
        return repository.signUp(email, password)
    }

    suspend fun onSignIn(email: String, password: String): Boolean {
        return repository.signIn(email, password)
    }

    fun onSignOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }
}