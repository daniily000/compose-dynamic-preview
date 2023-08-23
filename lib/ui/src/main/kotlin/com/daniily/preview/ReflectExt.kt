package com.daniily.preview

import kotlin.reflect.KCallable


/** Returns if this callable may return null.
 *
 * **Warning:** This method uses Kotlin Reflect, which works for debug only.
 * Do **NOT** use it in production code!
 *
 * This method does not invoke the callable.
 */
internal inline fun <reified T : Any?> KCallable<T?>.isNullable(): Boolean {
    return returnType.isMarkedNullable
}
