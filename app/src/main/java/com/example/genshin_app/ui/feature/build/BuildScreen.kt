package com.example.genshin_app.ui.feature.build

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.genshin_app.data.remote.dto.BuildDto
import com.example.genshin_app.data.remote.dto.BuildRequest

@Composable
fun BuildScreen(
    navController: NavController,
    slug: String,
    viewModel: BuildViewModel,
    onNavigateHome: () -> Unit,
) {
    val builds by viewModel.builds.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showCreate by remember { mutableStateOf(false) }
    var editTarget by remember { mutableStateOf<BuildDto?>(null) }

    LaunchedEffect(slug) {
        viewModel.loadBuilds(slug)
    }

    Scaffold (
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { onNavigateHome() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                    }

                    Text(
                        text = "Builds for: $slug",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Button(onClick = { showCreate = true }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(6.dp))
                        Text("Tambah")
                    }
                }
            }
        }
    ) { padding ->


        Column(modifier = Modifier.padding(16.dp)) {


            Spacer(modifier = Modifier.height(12.dp))

            if (loading) {
                CircularProgressIndicator()
                return@Column
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(builds) { b ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {

                            // ===== TITLE =====
                            Text(
                                text = b.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(4.dp))

                            // ===== META BADGE =====
                            Text(
                                text = if (b.is_meta == 1) "META BUILD" else "NON-META",
                                color = if (b.is_meta == 1)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.labelSmall
                            )

                            Spacer(Modifier.height(6.dp))

                            // ===== DESCRIPTION =====
                            b.description?.let {
                                Text("Desc: $it", style = MaterialTheme.typography.bodySmall)
                            }

                            // ===== PLAYSTYLE =====
                            b.playstyle?.let {
                                Text("Playstyle: $it", style = MaterialTheme.typography.bodySmall)
                            }

                            Spacer(Modifier.height(6.dp))

                            // ===== META INFO =====
                            b.created_by?.let {
                                Text("By: $it", style = MaterialTheme.typography.labelSmall)
                            }

                            b.created_at?.let {
                                Text("Created: $it", style = MaterialTheme.typography.labelSmall)
                            }

                            b.updated_at?.let {
                                Text("Updated: $it", style = MaterialTheme.typography.labelSmall)
                            }

                            Spacer(Modifier.height(8.dp))

                            // ===== ACTION BUTTON =====
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { editTarget = b }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }

                                IconButton(onClick = {
                                    viewModel.deleteBuild(b.id) {
                                        viewModel.loadBuilds(slug)
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* ================= CREATE ================= */
    if (showCreate) {
        BuildEditorDialog(
            onDismiss = { showCreate = false },
            onSave = { title, desc, play, isMeta ->
                viewModel.createBuild(
                    slug,
                    BuildRequest(
                        title = title,
                        description = desc,
                        playstyle = play,
                        is_meta = if (isMeta) 1 else 0
                    )
                ) {
                    showCreate = false
                    viewModel.loadBuilds(slug)
                }
            }
        )
    }

    /* ================= EDIT ================= */

    editTarget?.let { target ->
        BuildEditorDialog(
            initialTitle = target.title,
            initialDesc = target.description,
            initialPlaystyle = target.playstyle,
            initialIsMeta = target.is_meta == 1,
            onDismiss = { editTarget = null },
            onSave = { title, desc, play, isMeta ->
                viewModel.updateBuild(
                    target.id,
                    BuildRequest(
                        title = title,
                        description = desc,
                        playstyle = play,
                        is_meta = if (isMeta) 1 else 0
                    )
                ) {
                    editTarget = null
                    viewModel.loadBuilds(slug)
                }
            }
        )
    }
    error?.let { msg ->
        ErrorDialog(
            message = msg,
            onDismiss = { viewModel.clearError() }
        )
    }

}
