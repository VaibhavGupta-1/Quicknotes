package com.example.quicknotes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quicknotes.NoteState
import com.example.quicknotes.NotesEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    state: NoteState,
    onEvent: (NotesEvent) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "New Note") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(NotesEvent.SaveNote)
                        onNavigateUp() // Go back after saving
                    }) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Save Note")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = { onEvent(NotesEvent.SetTitle(it)) },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.content,
                onValueChange = { onEvent(NotesEvent.SetContent(it)) },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}