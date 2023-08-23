package com.daniily.preview

import com.daniily.preview.Imports.composeRuntime
import com.daniily.preview.PropertyData.Companion.toPropertyData
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlin.reflect.KMutableProperty0

internal class PropertyClassBuilder {

    fun FileSpec.Builder.addPropertiesClassImports(): FileSpec.Builder = this
        .addImport(composeRuntime, "mutableStateOf")
        .addImport(composeRuntime, "getValue")
        .addImport(composeRuntime, "setValue")

    fun KSFunctionDeclaration.findDynamicParameters(): List<PropertyData> =
        parameters.filterDynamicPreviewParameter().map { it.toPropertyData() }

    private fun List<KSValueParameter>.filterDynamicPreviewParameter(): List<KSValueParameter> = filter { element ->
        element.annotations.any {
            val name = it.shortName.asString()
            name == "DynamicPreviewParameter"
        }
    }

    fun buildPropertiesClass(baseName: String, properties: List<PropertyData>): TypeSpec {
        val className = "${baseName}_Properties"
        val propertyListTypeName = List::class.asClassName()
            .parameterizedBy(
                KMutableProperty0::class.asClassName()
                    .parameterizedBy(
                        WildcardTypeName.producerOf(
                            Any::class.asClassName().copy(nullable = true)
                        )
                    )
            )
        val listInitializer = "listOf(${properties.joinToString { "::${it.name}" }})"
        val propertySpecs = properties.map {
            PropertySpec.builder(it.name, it.type.toTypeName())
                .mutable()
                .delegate("mutableStateOf(${it.defaultValue})")
                .build()
        } + PropertySpec.builder("list", propertyListTypeName).initializer(listInitializer).build()

        return TypeSpec
            .classBuilder(className)
            .addModifiers(KModifier.PRIVATE)
            .addProperties(propertySpecs)
            .build()
    }
}
