package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceResult(
    val id: Int,
    val position: Int,
    val points: Int,
    @SerialName("time_difference")
    val timeDifference: String? = null,
    val driver: Driver? = null
)