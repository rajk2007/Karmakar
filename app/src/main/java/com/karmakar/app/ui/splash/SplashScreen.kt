package com.karmakar.app.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.karmakar.app.ui.theme.KarmaBlack
import com.karmakar.app.ui.theme.KarmaGold
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(KarmaBlack),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Karmakar", color = KarmaGold)
    }

    LaunchedEffect(Unit) {
        delay(2000)
        onNext()
    }
}
