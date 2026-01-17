package com.example.genshin_app.data.repository.auth

import com.example.genshin_app.data.remote.api.AuthApi
import com.example.genshin_app.data.remote.dto.request.auth.LoginRequest
import com.example.genshin_app.data.remote.dto.request.auth.RegisterRequest
import com.example.genshin_app.data.remote.dto.response.ApiResponse

class NetworkAuthRepository(
    private val authApi: AuthApi,
) : AuthRepository {
    override suspend fun registerUser(
        email: String,
        password: String,
    ): ApiResponse =
        try {
            authApi.registerUser(
                RegisterRequest(email, password),
            )
        } catch (e: Exception) {
            ApiResponse.fromException(e)
        }

    override suspend fun loginUser(
        email: String,
        password: String,
    ): ApiResponse =
        try {
            authApi.loginUser(
                LoginRequest(email, password),
            )
        } catch (e: Exception) {
            ApiResponse.fromException(e)
        }

    override suspend fun logout(): ApiResponse =
        try {
            authApi.logout()
        } catch (e: Exception) {
            ApiResponse.fromException(e)
        }
}