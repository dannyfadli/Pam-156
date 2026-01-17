package com.example.genshin_app.ui.feature.build


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BuildEditorDialog(
    initialTitle: String = "",
    initialDesc: String? = null,
    initialPlaystyle: String? = null,
    initialIsMeta: Boolean = false,
    onDismiss: () -> Unit,
    onSave: (String, String?, String?, Boolean) -> Unit,
) {
    var title by remember { mutableStateOf(initialTitle) }
    var desc by remember { mutableStateOf(initialDesc ?: "") }
    var play by remember { mutableStateOf(initialPlaystyle ?: "") }
    var isMeta by remember { mutableStateOf(initialIsMeta) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onSave(
                    title,
                    desc.takeIf { it.isNotBlank() },
                    play.takeIf { it.isNotBlank() },
                    isMeta
                )
            }) { Text("Simpan") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Batal") }
        },
        text = {
            Column {
                OutlinedTextField(title, { title = it }, label = { Text("Title") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(desc, { desc = it }, label = { Text("Description") })
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(play, { play = it }, label = { Text("Playstyle") })
            }
        }
    )
}
