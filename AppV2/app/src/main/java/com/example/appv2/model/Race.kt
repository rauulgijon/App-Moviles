package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Race(
    val id: Int,

    // Mapeamos la columna 'name' de la BDD a 'raceName' para usarlo en Kotlin
    @SerialName("name")
    val raceName: String,

    // Mapeamos la columna 'circuit' de la BDD a 'circuitName'
    @SerialName("circuit")
    val circuitName: String,

    val date: String,

    // La ronda (round) puede venir como 0 si es antigua
    val round: Int = 0,

    // Mapeamos la columna 'image' (el mapa en la BDD) a 'circuitImage'
    @SerialName("image")
    val circuitImage: String? = null,

    // El ganador puede ser nulo (carreras futuras)
    val winner: String? = null,

    // Mapeamos la columna 'flag_image' de la BDD a 'flagImage'
    @SerialName("flag_image")
    val flagImage: String? = null
)