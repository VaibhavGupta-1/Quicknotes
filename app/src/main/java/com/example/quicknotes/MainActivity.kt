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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
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

class MainActivity : ComponentActivity() {

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
                    val authState by authViewModel.state.collectAsState()

                    // Create the ViewModel Factory for NotesViewModel
                    val viewModelFactory = ViewModelFactory(AppContainer.database.dao)
                    val notesViewModel: NotesViewModel = viewModel(factory = viewModelFactory)
                    val notesState by notesViewModel.state.collectAsState()

                    LaunchedEffect(key1 = Unit) {
                        if (googleAuthUiClient.getSignedInUser() != null) {
                            navController.navigate("notesList") {
                                popUpTo("auth") { inclusive = true }
                            }
                        }
                    }

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.signInWithIntent(intent = result.data ?: return@launch)
                                    authViewModel.onSignInResult(signInResult)
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = authState.isSignInSuccessful) {
                        if (authState.isSignInSuccessful) {
                            Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_LONG).show()
                            navController.navigate("notesList") { popUpTo("auth") { inclusive = true } }
                            authViewModel.resetState()
                        }
                    }

                    NavHost(navController = navController, startDestination = "auth") {
                        composable("auth") {
                            LoginScreen(onSignInClick = {
                                lifecycleScope.launch {
                                    val signInIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build())
                                }
                            })
                        }

                        composable("notesList") {
                            NotesListScreen(
                                state = notesState,
                                onEvent = notesViewModel::onEvent,
                                onAddNoteClick = { navController.navigate("addEditNote") },
                                onNoteClick = { /* Editing a note is a good next feature! */ },
                                onSignOutClick = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_LONG).show()
                                        navController.navigate("auth") { popUpTo("notesList") { inclusive = true } }
                                    }
                                }
                            )
                        }

                        composable("addEditNote") {
                            AddEditNoteScreen(
                                state = notesState,
                                onEvent = notesViewModel::onEvent,
                                onNavigateUp = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}