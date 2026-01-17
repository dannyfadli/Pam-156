package com.example.genshin_app.ui.feature.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.genshin_app.data.network.TokenManager

class AuthViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        isLoggedIn = !TokenManager.getToken().isNullOrBlank()
    }

    fun onLoginSuccess() {
        isLoggedIn = true
    }

    fun onLogout() {
        isLoggedIn = false
    }
}