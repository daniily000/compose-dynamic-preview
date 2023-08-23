package com.daniily.preview

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/** A Checkbox to display null state of a nullable property */
@Composable
internal fun NullCheckBox(isNull: Boolean, onCheck: (Boolean) -> Unit) {
    CompositionLocalProvider(LocalIndication provides rememberRipple()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .clickable(onClick = { onCheck(!isNull) })
        ) {
            Checkbox(
                checked = isNull,
                onCheckedChange = null,
            )
            Text(text = "Is null", style = MaterialTheme.typography.caption)
        }
    }
}

@Preview
@Composable
private fun NullCheckBoxPreview() {
    var isNull by remember { mutableStateOf(false) }
    NullCheckBox(isNull = isNull, onCheck = { isNull = !isNull })
}
