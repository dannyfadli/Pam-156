package com.example.genshin_app.ui.feature.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshin_app.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set
    var successMessage by mutableStateOf<String?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun register(
        email: String,
        password: String,
    ) {
        if (isLoading) return

        viewModelScope.launch {
            isLoading = true
            successMessage = null
            errorMessage = null

            try {
                val response =
                    withContext(Dispatchers.IO) {
                        authRepository.registerUser(
                            email = email,
                            password = password,
                        )
                    }

                when {
                    response.error != null -> {
                        errorMessage = response.error
                    }

                    response.message != null -> {
                        successMessage = response.message
                    }

                    else -> {
                        successMessage = "Registrasi berhasil"
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: "Registrasi gagal"
            } finally {
                isLoading = false
            }
        }
    }

    fun consumeSuccess() {
        successMessage = null
    }

    fun clearError(
        username: String,
        email: String,
        password: String,
    ) {
        if ((username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) || !isLoading) {
            errorMessage = null
        }
    }
}