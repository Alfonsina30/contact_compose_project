
package com.example.whatscontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val Orange = Color(0xFFFF7043)

data class Contact(val id: Int, val name: String, val phone: String)

val fakeContacts = listOf(
    Contact(1, "Roberto M", "+19472886504"),
    Contact(2, "Rossiel T", "+16047602089"),
    Contact(3, "Manuel R", "+18083090271"),
    Contact(4, "Frank", "+13215076133"),
    Contact(5, "Eduar", "+13477579383"),
    Contact(6, "Silvana", "+14077316247"),
    Contact(7, "Alonso", "+18993180964"),
    Contact(8, "Francesca", "+19550247390"),
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    var searching by remember { mutableStateOf(false) }
    val selectedIds = remember { mutableStateListOf<Int>() }

    if (searching) {
        SearchScreen(
            contacts = fakeContacts,
            selectedIds = selectedIds,
            onBack = { searching = false }
        )
    } else {
        MainScreen(
            selected = fakeContacts.filter { it.id in selectedIds },
            onAdd = { searching = true },
            onRemove = { contact -> selectedIds.remove(contact.id) }
        )
    }
}

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
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
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
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
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

@Composable
fun SearchScreen(
    contacts: List<Contact>,
    selectedIds: MutableList<Int>,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    BackHandler(onBack = onBack)

    val filtered = contacts.filter {
        it.name.contains(query, ignoreCase = true) || it.phone.contains(query)
    }

    Column(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Orange)
            }
            TextField(
                value = query,
                onValueChange = { query = it },
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
                IconButton(onClick = { query = "" }) {
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
                        onCheckedChange = { checked ->
                            if (checked) selectedIds.add(contact.id)
                            else selectedIds.remove(contact.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ContactRow(contact: Contact, trailing: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFCCBC)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF5D4037))
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(contact.name, fontWeight = FontWeight.Medium)
            Text(contact.phone, style = MaterialTheme.typography.bodySmall)
        }
        trailing()
    }
}






