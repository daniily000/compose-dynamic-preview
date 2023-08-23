package com.daniily.preview

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlin.reflect.KMutableProperty0

/** A visual representation of boolean property switcher for given property.
 *
 * Provides a visual representation using a Checkbox with a Text.
 *
 * Checkbox mutates the property, setting the opposite value. Text is set with property name.
 * Can be used in the following way for composing a preview:
 *
 * ```kotlin
 * class Properties {
 *     var state by remember { mutableStateOf(false) }
 * }
 *
 * @Preview
 * @Composable
 * fun Preview {
 *     val properties = remember { Properties() }
 *     Column {
 *         Surface(enabled = properties.state)
 *         BooleanPropertySwitcher(properties::state)
 *     }
 * }
 * ```
 *
 * @see PropertySwitchers
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BooleanPropertySwitcher(property: KMutableProperty0<Boolean>) {
    val current = property.get()
    val onClick = { property.set(!current) }
    Surface(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = current, onCheckedChange = { onClick() })
            Text(text = property.name)
        }
    }
}

private class BooleanPropertySwitcherExample {
    var state by mutableStateOf(false)
}

@Preview
@Composable
private fun BooleanPropertySwitcherPreview() {
    val switcher = remember { BooleanPropertySwitcherExample() }
    BooleanPropertySwitcher(switcher::state)
}
