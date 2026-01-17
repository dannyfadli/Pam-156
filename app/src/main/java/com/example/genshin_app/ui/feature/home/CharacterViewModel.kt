package com.example.genshin_app.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshin_app.data.remote.dto.CharacterDto
import com.example.genshin_app.data.repository.character.CharacterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repo: CharacterRepository,
) : ViewModel() {

    private val _characters = MutableStateFlow<List<CharacterDto>>(emptyList())
    private val _query = MutableStateFlow("")

    // 🔥 STATE YANG AKAN DI-COLLECT UI
    val characters: StateFlow<List<CharacterDto>> =
        combine(_characters, _query) { list, q ->
            val query = q.trim().lowercase()
            if (query.isEmpty()) list
            else list.filter {
                it.name.lowercase().contains(query) ||
                        it.slug.lowercase().contains(query)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val query: StateFlow<String> = _query.asStateFlow()

    fun loadCharacters() {
        viewModelScope.launch {
            repo.getCharacters()
                .onSuccess { _characters.value = it }
        }
    }

    fun setQuery(q: String) {
        _query.value = q
    }
}
