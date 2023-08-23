package com.daniily.preview

import kotlin.reflect.KClass

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
annotation class DynamicPreview {

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
    annotation class Wrapper(val wrapperClass: KClass<out Any>)

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
    annotation class PreviewClass(val annotationClass: KClass<out Annotation>)
}
