package com.example.genshin_app.data.remote.dto


import kotlinx.serialization.Serializable


@Serializable
data class SimpleResponse(
    val message: String? = null,
    val error: String? = null,
    val id: Int? = null,
)