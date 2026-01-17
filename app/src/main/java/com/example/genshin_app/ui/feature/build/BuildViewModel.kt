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


    fun loadBuilds(slug: String) {
        viewModelScope.launch {
            _loading.value = true
            repo.getBuilds(slug).onSuccess { list ->
                _builds.value = list
            }
            _loading.value = false
        }
    }


    fun createBuild(slug: String, req: BuildRequest, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val res = repo.createBuild(slug, req)
            res.onSuccess { id -> onResult(true, null) }.onFailure { e -> onResult(false, e.message) }
        }
    }


    fun updateBuild(id: Int, req: BuildRequest, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val res = repo.updateBuild(id, req)
            res.onSuccess { onResult(true, null) }.onFailure { e -> onResult(false, e.message) }
        }
    }


    fun deleteBuild(id: Int, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val res = repo.deleteBuild(id)
            res.onSuccess { onResult(true, null) }.onFailure { e -> onResult(false, e.message) }
        }
    }
}