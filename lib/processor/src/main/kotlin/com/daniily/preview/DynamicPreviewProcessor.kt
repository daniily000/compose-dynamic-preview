package com.daniily.preview

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo

class DynamicPreviewProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private val propertyClassBuilder = PropertyClassBuilder()
    private val previewFunctionBuilder = PreviewFunctionBuilder()

    private val defaultAnnotation = "com.daniily.preview.DynamicPreview"

    private fun Resolver.getFunctionDeclarationsAnnotatedWith(
        annotation: String
    ) = getSymbolsWithAnnotation(annotation).filterIsInstance<KSFunctionDeclaration>()

    private fun Sequence<KSAnnotated>.resolveMetaAnnotatedMethods(resolver: Resolver) = flatMap {
        if (it is KSClassDeclaration) {
            resolver.getFunctionDeclarationsAnnotatedWith(it.qualifiedClassName)
        } else {
            sequenceOf(it)
        }
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver
            .getSymbolsWithAnnotation(defaultAnnotation)
            .resolveMetaAnnotatedMethods(resolver)
            .filterIsInstance<KSFunctionDeclaration>()
            .forEach(::generatePreview)
        return emptyList()
    }

    private fun generatePreview(
        function: KSFunctionDeclaration,
    ) {
        val wrapperFunction = function.findWrapperFunction()
        val previewClass = function.findPreviewClass()

        with(propertyClassBuilder) {
            with(previewFunctionBuilder) {

                val baseName = function.simpleName.getShortName()
                val dynamicProperties = function.findDynamicParameters()

                val propertiesClassSpec = buildPropertiesClass(baseName, dynamicProperties)
                val previewFunctionSpec =
                    buildPreviewFunction(baseName, dynamicProperties, wrapperFunction, previewClass)

                val previewName = "${baseName}_DynamicPreview"
                val parentFile =
                    function.parent as? KSFile ?: error("Method $function must be declared at the top")
                val pkg = parentFile.packageName.asString()

                FileSpec.builder(pkg, previewName)
                    .addPropertiesClassImports()
                    .addPreviewFunctionImports()
                    .addType(propertiesClassSpec)
                    .addFunction(previewFunctionSpec)
                    .build()
                    .writeTo(codeGenerator, Dependencies(false, parentFile))
            }
        }
    }
}

class DynamicPreviewProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return DynamicPreviewProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}

val KSDeclaration.qualifiedClassName get() = "${packageName.asString()}.${simpleName.asString()}"
