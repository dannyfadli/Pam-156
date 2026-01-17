package com.example.genshin_app.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.genshin_app.ui.feature.auth.viewmodel.LogoutViewModel
import com.example.genshin_app.ui.theme.Dimens
import com.example.genshin_app.ui.theme.LocalAppColors

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: CharacterViewModel,
    logoutViewModel: LogoutViewModel,
    onNavigateLogin: () -> Unit,
) {
    var confirmLogoutPopup by remember { mutableStateOf(false) }
    val characters by viewModel.filteredCharacters.collectAsState()
    val query by viewModel.query.collectAsState()
    val colors = LocalAppColors.current

    LaunchedEffect(logoutViewModel.isLoggedOut) {
        if (logoutViewModel.isLoggedOut) {
            onNavigateLogin()
            logoutViewModel.consumeLogout()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadCharacters()
    }

    Scaffold(
        containerColor = colors.primaryBackground,
    ) { padding ->
    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = viewModel::setQuery,
            label = { Text("Cari karakter") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )


        Spacer(Modifier.height(8.dp))

        FilterBar(viewModel)

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(characters) { character ->
                CharacterCard(character) {
                    navController.navigate("builds/${character.slug}")
                }
            }
        }
    }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(Dimens.SpacingL)
                    .align(Alignment.BottomEnd)
            ) {
                FloatingActionButton(
                    onClick = {
                        confirmLogoutPopup = true
                    },
                    content = {
                        Text(
                            "Logout",
                            color = Color(0xFF000000),
                        )
                    }
                )
            }
        }
    }

    if (confirmLogoutPopup) {
        AlertDialog(
            onDismissRequest = { confirmLogoutPopup = false },
            confirmButton = {
                TextButton(onClick = {
                    confirmLogoutPopup = false
                    logoutViewModel.logout()
                }) {
                    Text(
                        "LOGOUT",
                        color = colors.warningButton,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmLogoutPopup = false }) {
                    Text(
                        "BATALKAN",
                        color = colors.textButton,
                    )
                }
            },
            shape = MaterialTheme.shapes.extraSmall,
            title = { Text("Logout dari akun Anda") },
            text = { Text("Logout dan kembali ke halaman login?") },
            containerColor = colors.primaryBackground,
            iconContentColor = colors.warningIcon,
            titleContentColor = colors.titleContent,
            textContentColor = colors.primaryText,
        )
    }
}

