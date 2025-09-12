// In file: auth/GoogleAuthUiClient.kt
package com.example.quicknotes.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.quicknotes.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth // This import should now work
import com.google.firebase.ktx.Firebase   // This import should now work
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.common.Scopes


// Data class to hold user information
data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    // This line should now work correctly
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): UserData? {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            user?.let {
                UserData(
                    userId = it.uid,
                    username = it.displayName,
                    profilePictureUrl = it.photoUrl?.toString()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.let {
        UserData(
            userId = it.uid,
            username = it.displayName,
            profilePictureUrl = it.photoUrl?.toString()
        )
    }

    // In file: auth/GoogleAuthUiClient.kt

// ... (keep the rest of the file the same)

    // In file: auth/GoogleAuthUiClient.kt

// ... (keep the rest of the file the same, especially the imports)

    // In file: auth/GoogleAuthUiClient.kt

// ... (keep the rest of the file the same)

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}