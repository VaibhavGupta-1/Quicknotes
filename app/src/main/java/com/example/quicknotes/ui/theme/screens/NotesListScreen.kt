// In file: NotesListScreen.kt
package com.example.quicknotes.ui.screens // <-- Make sure this matches your project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quicknotes.R // <-- This import is important
import com.example.quicknotes.ui.theme.QuickNotesTheme

// Dummy data class for now. We will replace this with our Room entity later.
data class Note(val id: Int, val title: String, val content: String, val timestamp: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    notes: List<Note>,
    onAddNoteClick: () -> Unit,
    onNoteClick: (Note) -> Unit,
    onSignOutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Quick Notes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNoteClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new note"
                )
            }
        }
    ) { paddingValues ->
        if (notes.isEmpty()) {
            EmptyState(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(notes) { note ->
                    NoteItem(
                        title = note.title,
                        contentPreview = note.content,
                        timestamp = note.timestamp,
                        onClick = { onNoteClick(note) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No notes yet",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Tap the '+' button to create your first note!",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesListScreenPreview_WithNotes() {
    val sampleNotes = listOf(
        Note(1, "Meeting Recap", "Discussed Q3 goals and project timelines.", "10:30 AM"),
        Note(2, "Grocery List", "Milk, eggs, bread, and coffee.", "Yesterday")
    )
    QuickNotesTheme {
        NotesListScreen(notes = sampleNotes, onAddNoteClick = {}, onNoteClick = {}, onSignOutClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun NotesListScreenPreview_Empty() {
    QuickNotesTheme {
        NotesListScreen(notes = emptyList(), onAddNoteClick = {}, onNoteClick = {}, onSignOutClick = {})
    }
}