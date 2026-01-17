package com.example.genshin_app.data.remote.api

import com.example.genshin_app.data.remote.dto.request.auth.LoginRequest
import com.example.genshin_app.data.remote.dto.request.auth.RegisterRequest
import com.example.genshin_app.data.remote.dto.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @POST("/register")
    suspend fun registerUser(
        @Body request: RegisterRequest,
    ): ApiResponse

    @POST("/login")
    suspend fun loginUser(
        @Body request: LoginRequest,
    ): ApiResponse

    @POST("/logout")
    suspend fun logout(): ApiResponse
}