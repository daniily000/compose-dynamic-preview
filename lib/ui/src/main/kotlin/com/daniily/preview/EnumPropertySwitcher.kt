package com.daniily.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
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

/** A visual representation of enum property switcher for given property.
 *
 * Provides a clickable Surface to set next value, a Text with property name, a string representation of current value
 * and a Checkbox to switch property to null and back (if property is nullable).
 *
 * Nullability is detected with Kotlin Reflect; it works only if the property is located inside something what is
 * visible by Reflect. For example, a Class inside a function cannot be examined, but being lifted to top-level makes it
 * visible for Reflect.
 *
 * Component click sets the next Enum value to this property. Next value is determined via Java Reflection. This
 * approach cancels the necessity of providing additional methods for Enum to determine the next value. Do not use such
 * approach in production and release code!
 *
 * You can use EnumPropertySwitcher for composing previews in the following way:
 * ```kotlin
 * enum class EnumProperty {
 *     One, Two, Three
 * }
 *
 * class Properties {
 *     var nonNullState: EnumProperty by remember { mutableStateOf(EnumProperty.One) }
 *     // Nullability enables null switcher
 *     var nullableState: EnumProperty? by remember { mutableStateOf(EnumProperty.One) }
 * }
 *
 * @Preview
 * @Composable
 * fun Preview {
 *     val properties = remember { Properties() }
 *     Column {
 *         SomeComponent1(enum = properties.nonNullState)
 *         SomeComponent2(enum = properties.nullableState)
 *         // Must cast enums to KMutableProperty0<Enum<*>?> because of implementation specifics
 *         EnumPropertySwitcher(properties::nonNullState as KMutableProperty0<Enum<*>?>)
 *         EnumPropertySwitcher(properties::nullableState as KMutableProperty0<Enum<*>?>)
 *     }
 * }
 * ```
 *
 * @see PropertySwitchers
 */
@Composable
internal fun <T : Enum<T>> EnumPropertySwitcher(property: KMutableProperty0<Enum<T>?>) {
    val current = property.get()
    val isInspected = LocalInspectionMode.current
    val nullable = if (!isInspected) property.isNullable() else true
    runCatching {
        val latest = current.latestNonNull()
        EnumPropertySwitcherComposable(
            name = property.name,
            value = latest.toString(),
            nullable = nullable,
            isNull = current == null,
            onNext = { property.set(latest.next()) },
            onCheckedChange = { set -> latest.takeIf { !set }.also(property::set) }
        )
    }.onFailure {
        error("Nullable enum property must have an initial non-null value")
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EnumPropertySwitcherComposable(
    name: String,
    value: String,
    nullable: Boolean,
    isNull: Boolean,
    onNext: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(onClick = onNext, enabled = !isNull, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp)
        ) {
            Text(text = "$name: ", style = MaterialTheme.typography.caption)
            Text(
                text = value,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 6.dp)
                    .weight(1f),
            )
            if (nullable) {
                NullCheckBox(isNull = isNull, onCheck = onCheckedChange)
            }
        }
    }
}

private class EnumPropertySwitcherExample {
    var nonNullState: Animal by mutableStateOf(Animal.Cat)

    // Nullable enums MUST have initial parameters set
    var nullableState: Animal? by mutableStateOf(Animal.Cat)

    enum class Animal {
        Cat, Dog, Mouse, Wolf, Elephant
    }
}

@Preview
@Composable
private fun EnumPropertySwitcherPreview() {
    val switcher = remember { EnumPropertySwitcherExample() }
    Column {
        EnumPropertySwitcher(switcher::nonNullState as KMutableProperty0<Enum<*>?>)
        EnumPropertySwitcher(switcher::nullableState as KMutableProperty0<Enum<*>?>)
    }
}
