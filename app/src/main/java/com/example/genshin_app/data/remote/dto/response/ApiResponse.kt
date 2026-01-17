package com.example.genshin_app.data.remote.dto.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException

@Serializable
data class ApiResponse(
    val message: String? = null,
    val error: String? = null,
    val userId: Int? = null,
    val token: String? = null,
) {
    companion object {
        fun fromException(e: Throwable): ApiResponse {
            val errorMessage =
                when (e) {
                    is HttpException -> parseHttpError(e)
                    else -> e.message ?: "Terjadi kesalahan"
                }

            return ApiResponse(error = errorMessage)
        }

        object ApiJson {
            val instance =
                Json {
                    ignoreUnknownKeys = true
                }
        }

        private fun parseHttpError(e: HttpException): String {
            val body = e.response()?.errorBody()?.string()

            if (body.isNullOrBlank()) {
                return "Terjadi kesalahan (${e.code()})"
            }

            return runCatching {
                ApiJson.instance
                    .decodeFromString<ApiResponse>(body)
                    .error
            }.getOrNull() ?: "Terjadi kesalahan (${e.code()})"
        }
    }
}