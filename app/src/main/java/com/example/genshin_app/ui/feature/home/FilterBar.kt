package com.example.genshin_app.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterBar(
    viewModel: CharacterViewModel
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        // ⭐ RARITY
        item { FilterChip("5★") { viewModel.setRarity(5) } }
        item { FilterChip("4★") { viewModel.setRarity(4) } }

        // 🔥 ELEMENT
        listOf(
            "Pyro",
            "Hydro",
            "Electro",
            "Cryo",
            "Anemo",
            "Geo",
            "Dendro"
        ).forEach { vision ->
            item {
                FilterChip(vision) {
                    viewModel.setVision(vision)
                }
            }
        }

        // 🗡 WEAPON
        listOf(
            "Sword",
            "Claymore",
            "Polearm",
            "Bow",
            "Catalyst"
        ).forEach { weapon ->
            item {
                FilterChip(weapon) {
                    viewModel.setWeapon(weapon)
                }
            }
        }

        // 🌍 NATION
        listOf(
            "Mondstadt",
            "Liyue",
            "Inazuma",
            "Sumeru",
            "Fontaine",
            "Natlan"
        ).forEach { nation ->
            item {
                FilterChip(nation) {
                    viewModel.setNation(nation)
                }
            }
        }

        // ♻ RESET
        item {
            FilterChip("Reset") {
                viewModel.setVision(null)
                viewModel.setWeapon(null)
                viewModel.setNation(null)
                viewModel.setRarity(null)
            }
        }
    }
}
