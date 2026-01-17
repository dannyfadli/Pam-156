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

class LogoutViewModel(
    private val authRepository: AuthRepository,
    private val authViewModel: AuthViewModel,
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var isLoggedOut by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun logout() {
        if (isLoading) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                withContext(Dispatchers.IO) {
                    authRepository.logout()
                }
                TokenManager.clearToken()
                isLoggedOut = true
                authViewModel.onLogout()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Logout gagal"
            } finally {
                isLoading = false
            }
        }
    }

    fun consumeLogout() {
        isLoggedOut = false
    }
}