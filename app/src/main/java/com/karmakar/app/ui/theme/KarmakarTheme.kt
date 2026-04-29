package com.karmakar.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KarmaColorScheme = darkColorScheme(
    primary = KarmaGold,
    onPrimary = Color.Black,
    background = KarmaBlack,
    onBackground = KarmaText,
    surface = KarmaSurface,
    onSurface = KarmaText,
    secondary = KarmaDarkGold,
    error = KarmaRed
)

@Composable
fun KarmakarTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = KarmaColorScheme, content = content)
}
