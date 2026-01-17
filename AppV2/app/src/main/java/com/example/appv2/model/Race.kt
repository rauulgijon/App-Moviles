package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Race(
    val id: Int,
    val name: String,
    val circuit: String,
    val date: String,
    val image: String? = null,     // Mapa del circuito
    val winner: String? = null,    // Ganador (si ya se corrió)
    @SerialName("flag_image")
    val flagImage: String? = null
)