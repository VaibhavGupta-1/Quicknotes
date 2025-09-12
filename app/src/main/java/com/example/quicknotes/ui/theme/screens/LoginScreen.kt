// In file: ui/screens/LoginScreen.kt
package com.example.quicknotes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quicknotes.R // Make sure to import your project's R file
import com.example.quicknotes.ui.theme.QuickNotesTheme

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // IMPORTANT: Replace this with your actual logo if you have one
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome to Quick Notes",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onSignInClick) {
            Text(text = "Sign in with Google")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    QuickNotesTheme {
        LoginScreen(onSignInClick = {})
    }
}