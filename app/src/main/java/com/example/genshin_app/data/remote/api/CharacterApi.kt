package com.example.genshin_app.data.remote.api


import com.example.genshin_app.data.remote.dto.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path


interface CharacterApi {
    @GET("/characters")
    suspend fun listCharacters(): List<CharacterDto>


    @GET("/characters/{slug}")
    suspend fun getCharacter(@Path("slug") slug: String): CharacterDto
}