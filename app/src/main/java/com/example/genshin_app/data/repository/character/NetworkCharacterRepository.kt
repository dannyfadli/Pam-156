package com.example.genshin_app.data.repository.character


import com.example.genshin_app.data.remote.api.CharacterApi
import com.example.genshin_app.data.remote.dto.CharacterDto


class NetworkCharacterRepository(private val api: CharacterApi) : CharacterRepository {
    override suspend fun getCharacters(): Result<List<CharacterDto>> {
        return try {
            Result.success(api.listCharacters())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}