// Create this in a new file, e.g., AuthViewModel.kt
package com.example.quicknotes

import androidx.lifecycle.ViewModel
import com.example.quicknotes.auth.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

class AuthViewModel: ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: UserData?) {
        _state.update { it.copy(
            isSignInSuccessful = result != null,
            signInError = if (result == null) "Sign in failed" else null
        )}
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}