package com.example.genshin_app.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genshin_app.data.remote.dto.CharacterDto
import com.example.genshin_app.data.repository.character.CharacterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableStateFlow<List<CharacterDto>>(emptyList())
    val characters = _characters.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _filter = MutableStateFlow(CharacterFilter())
    val filter = _filter.asStateFlow()

    fun setQuery(q: String) {
        _query.value = q
    }

    fun setVision(v: String?) {
        _filter.update { it.copy(vision = v) }
    }

    fun setWeapon(w: String?) {
        _filter.update { it.copy(weapon = w) }
    }

    fun setNation(n: String?) {
        _filter.update { it.copy(nation = n) }
    }

    fun setRarity(r: Int?) {
        _filter.update { it.copy(rarity = r) }
    }

    // 🔥 SEARCH + FILTER
    val filteredCharacters = combine(
        characters,
        query,
        filter
    ) { chars, q, f ->
        chars.filter {
            (q.isBlank() || it.name.contains(q, true)) &&
                    (f.vision == null || it.vision == f.vision) &&
                    (f.weapon == null || it.weapon == f.weapon) &&
                    (f.nation == null || it.nation == f.nation) &&
                    (f.rarity == null || it.rarity == f.rarity)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun loadCharacters() {
        viewModelScope.launch {
            repository.getCharacters()
                .onSuccess { list ->
                    _characters.value = list
                }
                .onFailure { error ->
                    // TODO: handle error (log / snackbar)
                    error.printStackTrace()
                }
        }
    }
}
