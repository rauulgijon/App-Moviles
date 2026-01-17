package com.example.appv2.model

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: Int,
    val title: String,
    val date: String,
    val image: String?,
    val url: String,
    val content: String? = null // Nuevo campo (puede ser nulo si no hay info)
)