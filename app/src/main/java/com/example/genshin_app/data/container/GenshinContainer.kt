package com.example.genshin_app.data.container


import com.example.genshin_app.data.network.AuthInterceptor
import com.example.genshin_app.data.remote.api.AuthApi
import com.example.genshin_app.data.remote.api.BuildApi
import com.example.genshin_app.data.remote.api.CharacterApi
import com.example.genshin_app.data.repository.auth.AuthRepository
import com.example.genshin_app.data.repository.auth.NetworkAuthRepository
import com.example.genshin_app.data.repository.build.BuildRepository
import com.example.genshin_app.data.repository.build.NetworkBuildRepository
import com.example.genshin_app.data.repository.character.CharacterRepository
import com.example.genshin_app.data.repository.character.NetworkCharacterRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


class GenshinContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:8080"
    private val json = Json { ignoreUnknownKeys = true }


    val client =
        OkHttpClient
            .Builder()
            .addInterceptor(AuthInterceptor())
            .build()


    private val retrofit: Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            ).build()


    private val authApi: AuthApi by lazy { retrofit.create(AuthApi::class.java) }
    private val charApi: CharacterApi by lazy { retrofit.create(CharacterApi::class.java) }
    private val buildApi: BuildApi by lazy { retrofit.create(BuildApi::class.java) }


    override val authRepository: AuthRepository by lazy { NetworkAuthRepository(authApi) }
    override val characterRepository: CharacterRepository by lazy { NetworkCharacterRepository(charApi) }
    override val buildRepository: BuildRepository by lazy { NetworkBuildRepository(buildApi) }
}