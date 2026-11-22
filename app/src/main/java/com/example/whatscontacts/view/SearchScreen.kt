package com.example.whatscontacts.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.whatscontacts.model.Contact

@Composable
fun SearchScreen(
    contacts: List<Contact>,
    query: String,
    selectedIds: Set<Int>,
    onQueryChange: (String) -> Unit,
    onToggle: (Int, Boolean) -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    val filtered = contacts.filter {
        it.name.contains(query, ignoreCase = true) || it.phone.contains(query)
    }

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) { // systemBarsPadding evitare che l'elenco finisca sotto la barra di navigazione
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
            }
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Search") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear", tint = Orange)
                }
            }
        }
        HorizontalDivider()
        LazyColumn {
            items(filtered, key = { it.id }) { contact ->
                ContactRow(contact) {
                    Checkbox(
                        checked = contact.id in selectedIds,
                        onCheckedChange = { checked -> onToggle(contact.id, checked) }
                    )
                }
            }
        }
    }
}
