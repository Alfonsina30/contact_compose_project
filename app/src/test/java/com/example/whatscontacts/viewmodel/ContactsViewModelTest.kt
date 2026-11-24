package com.example.whatscontacts.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ContactsViewModelTest {

    private lateinit var viewModel: ContactsViewModel

    @Before
    fun setUp() {
        viewModel = ContactsViewModel()
    }

    @Test
    fun `The initial state is empty`() {
        // Arrange: ViewModel nuovo (già fatto in setUp)

        // Act: leggo lo stato di partenza
        val state = viewModel.uiState.value

        // Assert
        assertFalse(state.searching)
        assertEquals("", state.query)
        assertTrue(state.selectedIds.isEmpty())
    }

    @Test
    fun `openSearch activates search`() {
        // Arrange: ViewModel nuovo (già fatto in setUp)

        // Act
        viewModel.openSearch()

        // Assert
        assertTrue(viewModel.uiState.value.searching)
    }

    @Test
    fun `closeSearch close the search and clean the text`() {
        // Arrange: ricerca aperta con del testo scritto
        viewModel.openSearch()
        viewModel.onQueryChange("ro")

        // Act
        viewModel.closeSearch()

        // Assert
        val state = viewModel.uiState.value
        assertFalse(state.searching)
        assertEquals("", state.query)
    }

    @Test
    fun `onQueryChange updates the text searched`() {
        // Arrange: ViewModel nuovo (già fatto in setUp)

        // Act
        viewModel.onQueryChange("rob")

        // Assert
        assertEquals("rob", viewModel.uiState.value.query)
    }

    @Test
    fun `toggleContact with true select the contact`() {
        // Arrange: ViewModel nuovo (già fatto in setUp)

        // Act
        viewModel.toggleContact(1, true)

        // Assert
        assertEquals(setOf(1), viewModel.uiState.value.selectedIds)
    }

    @Test
    fun `toggleContact with false unselect the contact`() {
        // Arrange: due contatti selezionati
        viewModel.toggleContact(1, true)
        viewModel.toggleContact(2, true)

        // Act: deseleziono il primo
        viewModel.toggleContact(1, false)

        // Assert
        assertEquals(setOf(2), viewModel.uiState.value.selectedIds)
    }

    @Test
    fun `select the same contact twice without creating a duplicate`() {
        // Arrange: contatto 1 già selezionato
        viewModel.toggleContact(1, true)

        // Act: lo seleziono di nuovo
        viewModel.toggleContact(1, true)

        // Assert
        assertEquals(1, viewModel.uiState.value.selectedIds.size)
    }

    @Test
    fun `removeContact cancel only the contact selected`() {
        // Arrange: due contatti selezionati
        viewModel.toggleContact(1, true)
        viewModel.toggleContact(2, true)

        // Act: rimuovo il primo
        viewModel.removeContact(1)

        // Assert
        assertEquals(setOf(2), viewModel.uiState.value.selectedIds)
    }

    @Test
    fun `The selection remains when the search is closed`() {
        // Arrange: ricerca aperta con un contatto selezionato
        viewModel.openSearch()
        viewModel.toggleContact(3, true)

        // Act
        viewModel.closeSearch()

        // Assert
        assertEquals(setOf(3), viewModel.uiState.value.selectedIds)
    }
}
