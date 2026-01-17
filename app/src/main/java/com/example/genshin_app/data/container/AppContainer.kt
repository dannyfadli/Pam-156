package com.example.genshin_app.data.container


import com.example.genshin_app.data.repository.auth.AuthRepository
import com.example.genshin_app.data.repository.character.CharacterRepository
import com.example.genshin_app.data.repository.build.BuildRepository


interface AppContainer {
    val authRepository: AuthRepository
    val characterRepository: CharacterRepository
    val buildRepository: BuildRepository
}