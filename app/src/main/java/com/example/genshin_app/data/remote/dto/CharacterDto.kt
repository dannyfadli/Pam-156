package com.example.genshin_app.data.remote.dto


import kotlinx.serialization.Serializable


@Serializable
data class CharacterDto(
    val id: Int? = null,
    val name: String,
    val slug: String,
    val vision: String? = null,
    val weapon: String? = null,
    val nation: String? = null,
    val rarity: Int? = null,
    val description: String? = null,
    val icon_url: String? = null,
)