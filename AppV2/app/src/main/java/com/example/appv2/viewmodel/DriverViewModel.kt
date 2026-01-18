package com.example.appv2.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.DriverRepository
import com.example.appv2.model.Driver
import kotlinx.coroutines.launch

class DriverViewModel(application: Application) : AndroidViewModel(application) {
    var drivers by mutableStateOf<List<Driver>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var selectedDriver by mutableStateOf<Driver?>(null)
        private set

    init {
        fetchDrivers()
    }

    private fun fetchDrivers() {
        viewModelScope.launch {
            isLoading = true
            drivers = DriverRepository.getDrivers()
            isLoading = false
        }
    }

    fun onDriverSelected(driver: Driver?) {
        selectedDriver = driver
    }
}