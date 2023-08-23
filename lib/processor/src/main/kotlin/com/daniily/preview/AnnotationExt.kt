package com.daniily.preview

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation

inline fun <reified T : Annotation> KSAnnotated.findAnnotation(): KSAnnotation? {
    val annotationClass = T::class
    return findAnnotation(annotationClass.simpleName ?: "", annotationClass.qualifiedName ?: "")
}

fun KSAnnotated.findAnnotation(simpleName: String, qualifiedName: String): KSAnnotation? {
    return this.annotations.filter {
        it.shortName.getShortName() == simpleName &&
                it.annotationType.resolve().declaration.qualifiedName?.asString() == qualifiedName
    }.firstOrNull()
}
