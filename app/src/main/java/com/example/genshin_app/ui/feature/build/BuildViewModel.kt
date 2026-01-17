package com.example.genshin_app.ui.feature.build


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshin_app.data.remote.dto.BuildDto
import com.example.genshin_app.data.remote.dto.BuildRequest
import com.example.genshin_app.data.repository.build.BuildRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class BuildViewModel(private val repo: BuildRepository) : ViewModel() {

    private val _builds = MutableStateFlow<List<BuildDto>>(emptyList())
    val builds: StateFlow<List<BuildDto>> = _builds

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun clearError() {
        _error.value = null
    }

    fun loadBuilds(slug: String) {
        viewModelScope.launch {
            _loading.value = true
            repo.getBuilds(slug)
                .onSuccess { _builds.value = it }
                .onFailure { _error.value = it.message ?: "Gagal memuat data" }
            _loading.value = false
        }
    }

    fun createBuild(slug: String, req: BuildRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.createBuild(slug, req)
                .onSuccess { onSuccess() }
                .onFailure { _error.value = it.message ?: "Gagal menambah build" }
        }
    }

    fun updateBuild(id: Int, req: BuildRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.updateBuild(id, req)
                .onSuccess { onSuccess() }
                .onFailure { _error.value = it.message ?: "Gagal mengubah build" }
        }
    }

    fun deleteBuild(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.deleteBuild(id)
                .onSuccess { onSuccess() }
                .onFailure { _error.value = it.message ?: "Gagal menghapus build" }
        }
    }
}
