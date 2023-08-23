package com.daniily.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/** This function provides the latest cached non-null value.
 *
 * Receiver must be either non-null at the initialization time or have default value.
 * You can use it this way:
 *
 * ```kotlin
 * var value: String? = someFunctionParameter
 * val latest: String = value.latestNonNull()
 * ```
 *
 * May be useful for displaying latest non-null state (for example when loading new value
 * which is not present at the time)
 */
@Composable
internal fun <T> T?.latestNonNull(defaultValue: T? = null): T {
    var latest by remember { mutableStateOf(this ?: defaultValue ?: error("Nothing to save")) }
    if (this != null) {
        latest = this
    }
    return latest
}
