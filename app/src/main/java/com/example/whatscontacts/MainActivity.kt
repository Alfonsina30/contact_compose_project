package com.example.whatscontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                ContactList(fakeContacts)
            }
        }
    }
}

@Composable
fun ContactList(contacts: List<Contact>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        items(contacts) { contact ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(contact.name)
                Text(contact.phone)
            }
        }
    }
}
