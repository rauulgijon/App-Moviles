package com.example.appv2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appv2.data.DriverRepository
import com.example.appv2.model.Driver
import kotlinx.coroutines.launch

class DriverViewModel : ViewModel() {
    private val repository = DriverRepository()

    var drivers by mutableStateOf<List<Driver>>(emptyList()); private set
    var isLoading by mutableStateOf(false); private set

    var selectedDriver by mutableStateOf<Driver?>(null); private set

    init { fetchDrivers() }

    fun fetchDrivers() {
        viewModelScope.launch {
            isLoading = true
            drivers = repository.getDrivers()
            isLoading = false
        }
    }

    fun onDriverClicked(driver: Driver) { selectedDriver = driver }
    fun onBackToGallery() { selectedDriver = null }
}