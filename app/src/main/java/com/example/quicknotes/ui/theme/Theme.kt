// In file: ui/theme/Theme.kt
package com.example.quicknotes.ui.theme // Make sure this package name matches yours

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color // Add this import if Android Studio doesn't do it automatically

// 1. Define the Light Mode color assignments
private val LightColorScheme = lightColorScheme(
    primary = DarkBlue,            // Color for App Bar, prominent buttons
    background = OffWhite,         // App's main background color
    surface = Color.White,         // Color for Cards, Sheets, Menus
    onPrimary = Color.White,       // Text color on top of the primary color (e.g., App Bar title)
    onBackground = TextPrimary,    // Text color on top of the background color
    onSurface = TextPrimary,       // Text color on top of surfaces like cards
    secondary = AccentColor        // Color for Floating Action Buttons
)

// 2. Define the Dark Mode color assignments
private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,           // A lighter blue for dark mode
    background = Color(0xFF121212), // A standard dark background
    surface = Color(0xFF1E1E1E),   // A slightly lighter dark for cards
    onPrimary = Color.Black,
    onBackground = OffWhite,
    onSurface = OffWhite,
    secondary = AccentColor
)

// 3. This function applies the correct theme (light or dark)
@Composable
fun QuickNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // This comes from Type.kt
        content = content
    )
}