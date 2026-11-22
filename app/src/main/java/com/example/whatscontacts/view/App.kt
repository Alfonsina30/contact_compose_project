package com.example.whatscontacts.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.whatscontacts.model.fakeContacts
import com.example.whatscontacts.viewmodel.ContactsViewModel

@Composable
fun App(viewModel: ContactsViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    if (state.searching) {
        SearchScreen(
            contacts = fakeContacts,
            query = state.query,
            selectedIds = state.selectedIds,
            onQueryChange = viewModel::onQueryChange,
            onToggle = viewModel::toggleContact,
            onBack = viewModel::closeSearch
        )
    } else {
        MainScreen(
            selected = fakeContacts.filter { it.id in state.selectedIds },
            onAdd = viewModel::openSearch,
            onRemove = { viewModel.removeContact(it.id) }
        )
    }
}
