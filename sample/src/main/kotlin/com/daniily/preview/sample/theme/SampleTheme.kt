package com.daniily.preview.sample.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SampleTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(content = content)
}
