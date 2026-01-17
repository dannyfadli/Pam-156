package com.example.genshin_app.data.repository.character


import com.example.genshin_app.data.remote.dto.CharacterDto


interface CharacterRepository {
    suspend fun getCharacters(): Result<List<CharacterDto>>
}