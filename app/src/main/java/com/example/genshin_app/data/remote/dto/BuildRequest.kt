package com.example.genshin_app.data.remote.dto


import kotlinx.serialization.Serializable


@Serializable
data class BuildRequest(
    val title: String,
    val description: String? = null,
    val playstyle: String? = null,
    val is_meta: Int = 0,
)