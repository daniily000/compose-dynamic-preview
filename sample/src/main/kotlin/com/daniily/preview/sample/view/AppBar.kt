package com.daniily.preview.sample.view

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.daniily.preview.DynamicPreview
import com.daniily.preview.DynamicPreviewParameter

@Composable
@DynamicPreview
fun AppBar(
    @DynamicPreviewParameter
    label: String
) {
    TopAppBar { Text(label) }
}
