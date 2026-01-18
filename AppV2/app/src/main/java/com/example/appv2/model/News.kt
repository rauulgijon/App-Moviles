package com.example.appv2.model

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: Int? = null,
    val title: String,
    val content: String,
    val date: String,
    val image: String? = null,
    val url: String? = null
)