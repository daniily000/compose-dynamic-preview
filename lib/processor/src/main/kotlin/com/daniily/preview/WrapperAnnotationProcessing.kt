package com.daniily.preview

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType

fun KSAnnotated.findWrapperAnnotation(): KSAnnotation? = findAnnotation<DynamicPreview.Wrapper>()

fun KSAnnotated.findWrapperFromMetaAnnotation(): KSFunctionDeclaration? {
    return annotations.mapNotNull {
        it.annotationType.annotations.mapNotNull { it.getWrapperFunction() }.firstOrNull()
    }.firstOrNull()
}

/** Get KSFunctionDeclaration from @[DynamicPreview.Wrapper].
 *
 * Given: @[DynamicPreview] as [KSAnnotation]
 *
 * Returns: @Composable function from [wrapperClass][DynamicPreview.Wrapper.wrapperClass]
 *
 * [DynamicPreview.Wrapper] has [wrapperClass][DynamicPreview.Wrapper.wrapperClass],
 * which must be an Object or a Class with a single Composable to wrap a preview content.
 * This method retrieves this function as KSFunctionDeclaration, if it exists.
 * Otherwise, `null` is returned.
 */
fun KSAnnotation.getWrapperFunction(): KSFunctionDeclaration? {
    return arguments
        .find { it.name?.asString() == DynamicPreview.Wrapper::wrapperClass.name }
        ?.run { value }
        ?.run { this as? KSType }
        ?.declaration
        ?.let { it as? KSClassDeclaration }
        ?.run {
            declarations.find {
                it.annotations.any { it.shortName.getShortName() == "Composable" } && it is KSFunctionDeclaration
            }
        } as? KSFunctionDeclaration
}

fun KSAnnotated.findWrapperFunction(): KSFunctionDeclaration? {
    return findWrapperAnnotation()?.getWrapperFunction() ?: findWrapperFromMetaAnnotation()
}
