package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceResult(
    val id: Int,
    @SerialName("driver_name") val driverName: String,
    val position: Int,
    val team: String,
    val time: String
)