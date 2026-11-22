package com.example.whatscontacts.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    fun openSearch() = _uiState.update { it.copy(searching = true) }

    fun closeSearch() = _uiState.update { it.copy(searching = false, query = "") }

    fun onQueryChange(text: String) = _uiState.update { it.copy(query = text) }

    fun toggleContact(id: Int, checked: Boolean) = _uiState.update {
        it.copy(selectedIds = if (checked) it.selectedIds + id else it.selectedIds - id)
    }

    fun removeContact(id: Int) = _uiState.update {
        it.copy(selectedIds = it.selectedIds - id)
    }
}
