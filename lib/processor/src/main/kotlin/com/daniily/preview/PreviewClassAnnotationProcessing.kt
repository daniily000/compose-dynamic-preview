package com.daniily.preview

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType

private fun KSAnnotated.findPreviewClassAnnotation(): KSAnnotation? = findAnnotation<DynamicPreview.PreviewClass>()

private fun KSAnnotated.findPreviewClassMetaAnnotation(): KSClassDeclaration? {
    return annotations.mapNotNull {
        it.annotationType.resolve().declaration.annotations.mapNotNull { it.getPreviewClass() }.firstOrNull()
    }.firstOrNull()
}

private fun KSAnnotation.getPreviewClass(): KSClassDeclaration? {
    return arguments
        .find { it.name?.asString() == DynamicPreview.PreviewClass::annotationClass.name }
        ?.run { value }
        ?.run { this as? KSType }
        ?.declaration
        ?.let { it as? KSClassDeclaration }
        ?.run {
            declarations
                .map {
                    findAnnotation("Composable", "androidx.compose.runtime.Composable")
                }
                .mapNotNull { it as? KSClassDeclaration }
                .firstOrNull()
        }
}

fun KSAnnotated.findPreviewClass(): KSClassDeclaration? {
    return findPreviewClassAnnotation()?.getPreviewClass() ?: findPreviewClassMetaAnnotation()
}
