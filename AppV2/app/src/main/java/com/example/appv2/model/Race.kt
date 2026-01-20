package com.example.appv2.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Race(
    val id: Int,

    @SerialName("name")
    val raceName: String,

    @SerialName("circuit")
    val circuitName: String,

    val date: String,

    val round: Int = 0,

    @SerialName("image")
    val circuitImage: String? = null,

    val winner: String? = null,

    @SerialName("flag_image")
    val flagImage: String? = null
)