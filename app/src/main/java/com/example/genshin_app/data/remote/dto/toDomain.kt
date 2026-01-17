package com.example.genshin_app.data.remote.dto

fun CharacterDto.toDomain(): CharacterDto {
    return CharacterDto(
        name = name,
        slug = slug,
        vision = vision,
        weapon = weapon,
        nation = nation,
        rarity = rarity ?: 4,
        description = description,
        icon_url = icon_url
    )
}
