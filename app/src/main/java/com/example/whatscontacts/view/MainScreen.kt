package com.example.whatscontacts.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.whatscontacts.model.Contact

val Orange = Color(0xFFFF7043)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    selected: List<Contact>,
    onAdd: () -> Unit,
    onRemove: (Contact) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Whats Contacts", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Orange,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = Orange,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add contacts")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)
                .padding(vertical = 20.dp)
                .fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Permission contact granted")
                Checkbox(checked = true, onCheckedChange = null, enabled = false)
            }

            if (selected.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Dont have contacts selected", fontWeight = FontWeight.Bold)
                }
            } else {
                Text(
                    "Contact selected",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(15.dp)
                )
                LazyColumn {
                    items(selected, key = { it.id }) { contact ->
                        ContactRow(contact) {
                            IconButton(onClick = { onRemove(contact) }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Remove",
                                    tint = Orange
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
