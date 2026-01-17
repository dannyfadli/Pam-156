package com.example.genshin_app.data.remote.dto


import kotlinx.serialization.Serializable


@Serializable
data class BuildDto(
    val id: Int,
    val character_id: Int,
    val user_id: Int,
    val title: String,
    val description: String? = null,
    val playstyle: String? = null,
    val is_meta: Int = 0,
    val created_at: String? = null,
    val updated_at: String? = null,
    val created_by: String? = null,
)