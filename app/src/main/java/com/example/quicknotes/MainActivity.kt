// In file: MainActivity.kt
package com.example.quicknotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicknotes.ui.screens.AddEditNoteScreen
import com.example.quicknotes.ui.screens.Note
import com.example.quicknotes.ui.screens.NotesListScreen
import com.example.quicknotes.ui.theme.QuickNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Call our new AppNavigation composable
                    AppNavigation()
                }
            }
        }
    }
}

// This is our new central navigation controller
@Composable
fun AppNavigation() {
    // 1. Create a NavController. 'rememberNavController' makes it survive recompositions.
    val navController = rememberNavController()

    // 2. Create sample data (we'll replace this with a database later)
    val sampleNotes = listOf(
        Note(1, "Meeting Recap", "Discussed Q3 goals and project timelines.", "10:30 AM"),
        Note(2, "Grocery List", "Milk, eggs, bread, and coffee.", "Yesterday"),
        Note(3, "Idea", "A new app idea for tracking plant watering schedules.", "2 days ago")
    )

    // 3. Define the NavHost, which defines the navigation graph.
    NavHost(
        navController = navController,
        startDestination = "notesList" // The "route" for our starting screen
    ) {
        // Define the "notesList" screen
        composable(route = "notesList") {
            NotesListScreen(
                notes = sampleNotes,
                onAddNoteClick = {
                    // Navigate to the add/edit screen when FAB is clicked
                    navController.navigate("addEditNote")
                },
                onNoteClick = { note ->
                    // Later, we will pass the note ID here to edit it
                    navController.navigate("addEditNote")
                },
                onSignOutClick = { /* Handle sign out later */ }
            )
        }

        // Define the "addEditNote" screen
        composable(route = "addEditNote") {
            AddEditNoteScreen(
                initialTitle = "", // For now, it's always a new note
                initialContent = "",
                onSaveNote = { title, content ->
                    // Later, we will save the note data here
                    navController.popBackStack() // Go back to the previous screen (NotesList)
                },
                onNavigateUp = {
                    navController.popBackStack() // Go back when the back arrow is pressed
                }
            )
        }
    }
}