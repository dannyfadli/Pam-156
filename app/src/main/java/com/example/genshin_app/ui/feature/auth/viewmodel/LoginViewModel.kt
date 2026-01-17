package com.example.genshin_app.ui.feature.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshin_app.data.network.TokenManager
import com.example.genshin_app.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val authViewModel: AuthViewModel,
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set
    var successMessage by mutableStateOf<String?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var userId: Int? = null

    fun login(
        email: String,
        password: String,
    ) {
        if (isLoading) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            successMessage = null
            errorMessage = null

            try {
                val response =
                    withContext(Dispatchers.IO) {
                        authRepository.loginUser(
                            email = email,
                            password = password,
                        )
                    }

                when {
                    response.error != null -> {
                        errorMessage = response.error
                    }

                    response.token.isNullOrBlank() -> {
                        errorMessage = "Terjadi kesalahan saat login"
                    }

                    else -> {
                        TokenManager.saveToken(response.token)
                        userId = response.userId
                        successMessage = response.message ?: "Berhasil login"
                        authViewModel.onLoginSuccess()
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Login gagal"
            } finally {
                isLoading = false
            }
        }
    }

    fun consumeSuccess() {
        successMessage = null
    }

    fun clearError(
        email: String,
        password: String,
    ) {
        if ((email.isNotBlank() && password.isNotBlank()) || !isLoading) {
            errorMessage = null
        }
    }
}