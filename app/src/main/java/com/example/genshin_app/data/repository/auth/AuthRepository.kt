package com.example.genshin_app.data.repository.auth

import com.example.genshin_app.data.remote.dto.response.ApiResponse

interface AuthRepository {
    suspend fun registerUser(
        email: String,
        password: String,
    ): ApiResponse

    suspend fun loginUser(
        email: String,
        password: String,
    ): ApiResponse

    suspend fun logout(): ApiResponse
}