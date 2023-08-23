package com.daniily.preview

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType

private fun KSAnnotated.findPreviewClassAnnotation(): KSAnnotation? =
    findAnnotation<DynamicPreview.PreviewClass>()

private fun KSAnnotated.findPreviewClassMetaAnnotation(): KSClassDeclaration? {
    val declaration = annotations.mapNotNull { annotation ->
        val innerAnnotations = annotation.annotationType.resolve().declaration.annotations
        innerAnnotations.mapNotNull {
            it.getPreviewClass()
        }
            .firstOrNull()
    }.firstOrNull()
    return declaration
}

private fun KSAnnotation.getPreviewClass(): KSClassDeclaration? {
    val previewClass = arguments
        .find { it.name?.asString() == DynamicPreview.PreviewClass::annotationClass.name }
        ?.run { value }
        ?.run { this as? KSType }
        ?.declaration
        ?.let { it as? KSClassDeclaration }
    return previewClass
}

fun KSAnnotated.findPreviewClass(): KSClassDeclaration? {
    return findPreviewClassAnnotation()?.getPreviewClass() ?: findPreviewClassMetaAnnotation()
}
