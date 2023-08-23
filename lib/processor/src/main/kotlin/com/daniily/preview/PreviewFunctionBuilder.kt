package com.daniily.preview

import com.daniily.preview.Imports.Composable
import com.daniily.preview.Imports.Preview
import com.daniily.preview.Imports.composeMaterial
import com.daniily.preview.Imports.composeRuntime
import com.daniily.preview.Imports.composeUi
import com.daniily.preview.Imports.foundationLayout
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toClassName

internal class PreviewFunctionBuilder {

    fun FileSpec.Builder.addPreviewFunctionImports(): FileSpec.Builder = this
        .addImport(composeRuntime, "remember")
        .addImport(composeMaterial, "Surface")
        .addImport(foundationLayout, "Column")
        .addImport(foundationLayout, "fillMaxSize")
        .addImport(foundationLayout, "padding")
        .addImport(composeUi, "Modifier")
        .addImport("com.daniily.preview", "PropertySwitchers")
        .addImport("androidx.compose.ui.unit", "dp")

    fun buildPreviewFunction(
        baseName: String,
        properties: List<PropertyData>,
        wrapperFunction: KSFunctionDeclaration? = null,
        previewAnnotation: KSClassDeclaration? = null
    ): FunSpec {

        val actualPreviewAnnotation = previewAnnotation?.toClassName() ?: Preview

        val previewName = "${baseName}_DynamicPreview"

        val argsIndent = "            "
        val args = properties.joinToString(separator = ",\n$argsIndent") { "${it.name} = properties.${it.name}" }

        val wrapperInstantiation = wrapperFunction?.let {
            val parent = it.parent as KSClassDeclaration
            when (parent.classKind) {
                ClassKind.CLASS -> parent.qualifiedClassName + "()"
                ClassKind.OBJECT -> parent.qualifiedClassName
                else -> null
            }
        }

        val wrapperStart = wrapperInstantiation?.plus(".${wrapperFunction.simpleName.asString()}")

        return FunSpec
            .builder(previewName)
            .addAnnotation(Composable)
            .addAnnotation(actualPreviewAnnotation)
            .addModifiers(KModifier.PRIVATE)
            .addCode(
                buildCodeBlock {
                    indent()
                    beginControlFlow("val properties = remember")
                    addStatement("${baseName}_Properties()")
                    endControlFlow()
                    wrapperStart?.let { beginControlFlow(it) }
                    addStatement("$baseName(")
                    indent()
                    addStatement(args)
                    unindent()
                    addStatement(")")
                    addStatement("properties.list.PropertySwitchers()")
                    unindent()
                    wrapperStart?.also { endControlFlow() }
                }
            )
            .build()
    }
}
