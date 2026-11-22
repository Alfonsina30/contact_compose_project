package com.example.whatscontacts.viewmodel

data class ContactsUiState(
    val searching: Boolean = false,
    val query: String = "",
    val selectedIds: Set<Int> = emptySet()
)
