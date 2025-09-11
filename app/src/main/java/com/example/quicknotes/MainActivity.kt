// In file: MainActivity.kt
package com.example.quicknotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quicknotes.auth.GoogleAuthUiClient
import com.example.quicknotes.ui.screens.*
import com.example.quicknotes.ui.theme.QuickNotesTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import com.example.quicknotes.ui.screens.LoginScreen

class MainActivity : ComponentActivity() {

    // Instantiate our Google Auth client
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()
                    val state by authViewModel.state.collectAsStateWithLifecycle()

                    // This effect runs once when the app starts to check if a user is already signed in
                    LaunchedEffect(key1 = Unit) {
                        if(googleAuthUiClient.getSignedInUser() != null) {
                            navController.navigate("mainApp") {
                                popUpTo("auth") { inclusive = true }
                            }
                        }
                    }

                    // The launcher that gets the result from the Google Sign-In pop-up
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    authViewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    // Effect to handle navigation after a successful sign-in
                    LaunchedEffect(key1 = state.isSignInSuccessful) {
                        if(state.isSignInSuccessful) {
                            Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_LONG).show()
                            navController.navigate("mainApp") {
                                popUpTo("auth") { inclusive = true }
                            }
                            authViewModel.resetState()
                        }
                    }

                    // Main Navigation Host
                    NavHost(navController = navController, startDestination = "auth") {
                        // Authentication Graph (Login Screen)
                        composable("auth") {
                            LoginScreen(
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        // Main App Graph (Notes Screens)
                        composable("mainApp") {
                            val sampleNotes = listOf(
                                Note(1, "Meeting Recap", "Discussed Q3 goals...", "10:30 AM"),
                                Note(2, "Grocery List", "Milk, eggs, bread...", "Yesterday")
                            )
                            NotesListScreen(
                                notes = sampleNotes,
                                onAddNoteClick = { /* navController.navigate("addEditNote") */ },
                                onNoteClick = { /* navController.navigate("addEditNote") */ },
                                onSignOutClick = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_LONG).show()
                                        navController.navigate("auth") {
                                            popUpTo("mainApp") { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                        // Add composable for AddEditNoteScreen if needed
                    }
                }
            }
        }
    }
}