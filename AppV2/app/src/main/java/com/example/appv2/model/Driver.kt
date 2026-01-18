package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val team: String?,
    val number: Int?,
    val image: String?,
    val country: String? = "Desconocido",
    @SerialName("total_points") // Mapea el cálculo de la vista SQL
    val points: Int? = 0,
    @SerialName("world_championships")
    val worldChampionships: Int? = 0
)