package com.example.movieapp.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SubtitlePrimary(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall
    )
}