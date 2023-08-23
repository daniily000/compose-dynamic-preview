package com.daniily.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.reflect.KMutableProperty0

/** A visual representation of string property switcher for given property.
 *
 * Provides a Text with property name, a field with current string value
 * and a Checkbox to switch property to null and back (if property is nullable).
 *
 * Nullability is detected with Kotlin Reflect; it works only if the property is located inside something what is
 * visible by Reflect. For example, a Class inside a function cannot be examined, but being lifted to top-level makes it
 * visible for Reflect.
 *
 * You can use StringPropertySwitcher for composing previews in the following way:
 *
 * ```kotlin
 * class Properties {
 *     var nonNullString: String by remember { mutableStateOf("value") }
 *     // Nullability enables null switcher
 *     // Null is acceptable for the initial value
 *     var nullableString: String? by remember { mutableStateOf(null) }
 * }
 *
 * @Preview
 * @Composable
 * fun Preview {
 *     val properties = remember { Properties() }
 *     Column {
 *         SomeComponent1(text = properties.nonNullString)
 *         SomeComponent2(text = properties.nullableString)
 *         StringPropertySwitcher(properties::nonNullState)
 *         StringPropertySwitcher(properties::nullableState)
 *     }
 * }
 * ```
 *
 * @see PropertySwitchers
 */
@Composable
fun StringPropertySwitcher(property: KMutableProperty0<String?>) {
    val current = property.get()
    val isInspected = LocalInspectionMode.current
    val nullable = if (!isInspected) property.isNullable() else true
    val latestNonnull = current.latestNonNull("")
    StringPropertySwitcherComponent(
        name = property.name,
        value = latestNonnull,
        onChange = { property.set(it) },
        nullable = nullable,
        isNull = current == null,
        onCheckedChange = { set -> latestNonnull.takeIf { !set }.also(property::set) }
    )
}

@Composable
private fun StringPropertySwitcherComponent(
    name: String,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    nullable: Boolean = true,
    isNull: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {
    Surface(modifier = modifier) {
        Box(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(text = "$name: ", style = MaterialTheme.typography.caption)
                BasicTextField(
                    value = value,
                    onValueChange = onChange,
                    textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface),
                    modifier = Modifier
                        .weight(weight = 1f)
                        .background(color = MaterialTheme.colors.secondary.copy(alpha = 0.1f))
                        .padding(vertical = 6.dp, horizontal = 4.dp)
                )

                if (nullable) {
                    NullCheckBox(isNull = isNull, onCheck = onCheckedChange)
                }
            }
        }
    }
}

private class StringPropertySwitcherExample {
    var state: String? by mutableStateOf("string")
}
@Preview
@Composable
private fun StringPropertySwitcherPreview() {
    val switcher = remember { StringPropertySwitcherExample() }
    StringPropertySwitcher(switcher::state)
}
