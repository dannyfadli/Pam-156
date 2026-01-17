package com.example.genshin_app.ui.feature.build

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
) {
    val builds by viewModel.builds.collectAsState()
    val loading by viewModel.loading.collectAsState()

    var showCreate by remember { mutableStateOf(false) }
    var editTarget by remember { mutableStateOf<BuildDto?>(null) }

    LaunchedEffect(slug) {
        viewModel.loadBuilds(slug)
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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

        Spacer(modifier = Modifier.height(12.dp))

        if (loading) {
            CircularProgressIndicator()
            return@Column
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(builds) { b ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(b.title, style = MaterialTheme.typography.titleSmall)
                            b.playstyle?.let { Text(it) }
                        }

                        Row {
                            IconButton(onClick = { editTarget = b }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }

                            IconButton(onClick = {
                                viewModel.deleteBuild(b.id) { ok, _ ->
                                    if (ok) viewModel.loadBuilds(slug)
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
                        is_meta = if (isMeta) 1 else 0 // ✅ Boolean → Int
                    )
                ) { ok, _ ->
                    if (ok) {
                        showCreate = false
                        viewModel.loadBuilds(slug)
                    }
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
            initialIsMeta = target.is_meta == 1, // ✅ Int → Boolean
            onDismiss = { editTarget = null },
            onSave = { title, desc, play, isMeta ->
                viewModel.updateBuild(
                    target.id,
                    BuildRequest(
                        title = title,
                        description = desc,
                        playstyle = play,
                        is_meta = if (isMeta) 1 else 0 // ✅ Boolean → Int
                    )
                ) { ok, _ ->
                    if (ok) {
                        editTarget = null
                        viewModel.loadBuilds(slug)
                    }
                }
            }
        )
    }
}
