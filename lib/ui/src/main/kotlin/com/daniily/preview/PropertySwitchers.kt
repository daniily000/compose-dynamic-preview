package com.daniily.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniily.preview.BooleanPropertySwitcher
import kotlin.reflect.KMutableProperty0

/** A component to display all available property switchers for a received list of properties.
 *
 * For now it supports the following types – Boolean, String?, and Enum?
 *
 * You need to have a list of properties which will be used to display a property switchers list.
 * The list displays a [Column] with a header and a number of property switchers for each type in it. If a type is
 * unsupported, [IllegalStateException] is thrown. [additionalContent] is put inside the column after all property
 * switchers.
 *
 * Function is intended to be used in the following way:
 *
 * ```kotlin
 * // Create a top-level properties class
 * private class Properties {
 *
 *     enum class PropertyEnums {
 *         Enum1, Enum2, Enum3
 *     }
 *
 *     var boolean by mutableStateOf(false)
 *     var string by mutableStateOf("string")
 *     var nullString: String? by mutableStateOf(null)
 *     var enum: PropertyEnums by mutableStateOf(PropertyEnums.Enum1)
 *     var nullEnum: PropertyEnums? by mutableStateOf(PropertyEnums.Enum1) // Must not be null for the first time
 *
 *     // create a list with all the properties
 *     val list = listOf(
 *         ::boolean,
 *         ::string,
 *         ::nullString,
 *         ::enum,
 *         ::nullEnum,
 *     )
 * }
 *
 * @Preview
 * @Composable
 * private fun PropertySwitchersPreview() {
 *     val properties = remember { Properties() } // Remember a property class
 *     Column {
 *         ExaminedComponent(
 *             properties.boolean,
 *             properties.string,
 *             properties.nullString,
 *             properties.enum,
 *             properties.nullEnum
 *         )
 *         properties.list.PropertySwitchers {
 *             ClearFocusButton() // Add additional 'Clear Focus' button to the list
 *         }
 *     }
 * }
 * ```
 *
 * @param additionalContent A Composable which is put into a [Column]
 *
 * @see BooleanPropertySwitcher
 * @see StringPropertySwitcher
 * @see EnumPropertySwitcher
 */
@Composable
fun List<KMutableProperty0<*>>.PropertySwitchers(
    additionalContent: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Component parameters",
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp),
                style = MaterialTheme.typography.subtitle2
            )
            Divider()
        }
        forEach {
            val value = it.get()
            @Suppress("UNCHECKED_CAST") when (value) {
                is Boolean -> BooleanPropertySwitcher(property = it as KMutableProperty0<Boolean>)
                is String? -> StringPropertySwitcher(property = it as KMutableProperty0<String?>)
                is Enum<*>? -> EnumPropertySwitcher(property = it as KMutableProperty0<Enum<*>?>)
                else -> error("Unsupported property type")
            }
            Divider()
        }
        additionalContent()
    }
}

private class Properties {

    enum class PropertyEnums {
        Enum1, Enum2, Enum3
    }

    var boolean by mutableStateOf(false)
    var string by mutableStateOf("string")
    var nullString: String? by mutableStateOf(null)
    var enum: PropertyEnums by mutableStateOf(PropertyEnums.Enum1)
    var nullEnum: PropertyEnums? by mutableStateOf(PropertyEnums.Enum1) // Must be declared for first time

    val list = listOf(
        ::boolean,
        ::string,
        ::nullString,
        ::enum,
        ::nullEnum,
    )
}

@Preview
@Composable
private fun PropertySwitchersPreview() {
    val properties = remember { Properties() }
    Column {
        properties.list.PropertySwitchers {
            ClearFocusButton()
        }
    }
}
