package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val id: Int,
    val name: String,
    val base: String?,
    @SerialName("team_principal")
    val teamPrincipal: String?,
    val logo: String?,
    @SerialName("car_image")
    val carImage: String? = null,
    val points: Int? = 0
)