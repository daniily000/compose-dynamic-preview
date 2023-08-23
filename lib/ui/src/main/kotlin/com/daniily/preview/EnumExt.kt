package com.daniily.preview

/** Return next enum by ordinal (with the first one after the last one)
 *
 * This method extends all the `enum` classes, making it available for every
 * enum instance. It returns the next instance after this one. For the last instance,
 * the first is returned, as for a cycle.
 *
 * It uses Java Reflect under the hood, so use it with caution, considering performance.
 */
internal fun <T : Enum<T>> Enum<T>.next(): T {
    val values = javaClass.methods.find { it.name == "values" }?.invoke(null) as Array<Enum<T>>
    return values[(ordinal + 1) % values.size] as T
}
