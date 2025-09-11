// In file: ui/screens/AddEditNoteScreen.kt
package com.example.quicknotes.ui.screens // <-- Make sure this matches your project

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quicknotes.ui.theme.QuickNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    // We pass initial values so we can use this screen for both adding (empty) and editing (filled)
    initialTitle: String,
    initialContent: String,
    onSaveNote: (title: String, content: String) -> Unit,
    onNavigateUp: () -> Unit // This function will handle the back button press
) {
    // 'remember' holds the state of the text fields
    var title by remember { mutableStateOf(initialTitle) }
    var content by remember { mutableStateOf(initialContent) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (initialTitle.isEmpty()) "New Note" else "Edit Note") },
                // The navigation icon is the button on the far left (e.g., a back arrow)
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                // Actions are the buttons on the right side
                actions = {
                    IconButton(onClick = { onSaveNote(title, content) }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save Note"
                        )
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
            // Text field for the note title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.titleLarge,
                singleLine = true // Ensures the title is on one line
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Text field for the note content
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // This makes the content field take all available vertical space
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// Preview for creating a new note
@Preview(showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    QuickNotesTheme {
        AddEditNoteScreen(
            initialTitle = "",
            initialContent = "",
            onSaveNote = { _, _ -> },
            onNavigateUp = {}
        )
    }
}

// Preview for editing an existing note
@Preview(showBackground = true)
@Composable
fun EditNoteScreenPreview() {
    QuickNotesTheme {
        AddEditNoteScreen(
            initialTitle = "My Shopping List",
            initialContent = "- Apples\n- Bananas\n- Cereal",
            onSaveNote = { _, _ -> },
            onNavigateUp = {}
        )
    }
}