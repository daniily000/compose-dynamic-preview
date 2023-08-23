package com.daniily.preview

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview


/** A button to clear the focus from the screen.
 *
 * It is useful for previews with focusable components, such as TextField or others.
 *
 * Just put this button as-is to clear all the focus on click.
 */
@Composable
fun ClearFocusButton() {
    val focusManager = LocalFocusManager.current
    TextButton(onClick = { focusManager.clearFocus() }) {
        Text(text = "Clear focus", color = MaterialTheme.colors.secondary)
    }
}

@Preview
@Composable
private fun ClearFocusButtonPreview() {
    ClearFocusButton()
}
