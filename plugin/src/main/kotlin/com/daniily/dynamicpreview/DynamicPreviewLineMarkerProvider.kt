package com.daniily.dynamicpreview

import com.android.tools.compose.isComposableFunction
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.icons.AllIcons
import com.intellij.icons.AllIcons.RunConfigurations.TestState.Run
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode
import org.jetbrains.rpc.LOG

private val annotationPackage = "com.daniily.preview"
private val annotationName = "DynamicPreview"
private val dynamicPreviewAnnotationFq = "$annotationPackage.$annotationName"

class DynamicPreviewLineMarkerProvider : LineMarkerProviderDescriptor() {

    override fun getName(): String = "DynamicPreviewLineMarkerProvider"

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {

        if (element is KtNamedFunction) {
            LOG.warn("${element.name} is KtNamedFunction")

            if (element.isComposableFunction()) LOG.warn("${element.name} is Composable")
            else LOG.warn("${element.name} is NOT a Composable")

            if (element.hasDynamicPreview()) LOG.warn("${element.name} has DynamicPreview")
            else LOG.warn("${element.name} has NO DynamicPreview")

            if (element.mustBePreviewed()) LOG.warn("${element.name} inherits DynamicPreview")
            else LOG.warn("${element.name} does NOT inherit DynamicPreview")
        }


        return if (element is KtNamedFunction && element.isComposableFunction() && element.mustBePreviewed()) {
            LOG.warn("Annotation entries: ${element.annotationEntries}")

            val funKeyword = element.funKeyword ?: return null
            val navHandler = DynamicPreviewNavigationHandler()
            if (!navHandler.isPreviewFunctionAvailable(element)) {
                val makeHandler = MakeDynamicPreviewHandler()
                LineMarkerInfo(
                    /* element = */ element,
                    /* range = */ funKeyword.textRange,
                    /* icon = */ AllIcons.Actions.Compile,
                    /* tooltipProvider = */ { "Rebuild to generate Dynamic Preview" },
                    /* navHandler = */ makeHandler,
                    /* alignment = */ GutterIconRenderer.Alignment.LEFT,
                    /* accessibleNameProvider = */ { "" }
                )
            } else {
                LineMarkerInfo(
                    /* element = */ element,
                    /* range = */ funKeyword.textRange,
                    /* icon = */ Run,
                    /* tooltipProvider = */ { "Run Dynamic Preview" },
                    /* navHandler = */ navHandler,
                    /* alignment = */ GutterIconRenderer.Alignment.LEFT,
                    /* accessibleNameProvider = */ { "" }
                )
            }
        } else {
            null
        }
    }
}

private fun KtAnnotationEntry.getQualifiedName(): String? {
    val annotationDescriptor = analyze(BodyResolveMode.PARTIAL)
        .get(BindingContext.ANNOTATION, this)
    return if (annotationDescriptor == null) {
        null
    } else {
        annotationDescriptor.fqName?.asString()
    }
}

private fun KtAnnotationEntry.fqNameMatches(fqName: String): Boolean {
    val name = shortName?.asString()
    return if (name == null) {
        false
    } else {
        fqName.endsWith(name, false) && fqName == getQualifiedName()
    }
}

private fun KtAnnotationEntry.isDynamicPreview(): Boolean {
    return shortName?.identifier == annotationName && fqNameMatches(dynamicPreviewAnnotationFq)
}

private fun KtAnnotated.mustBePreviewed(): Boolean = hasDynamicPreview() || annotationEntries
    .any(KtAnnotationEntry::inheritsDynamicPreview)

private fun KtAnnotated.hasDynamicPreview(): Boolean =
    annotationEntries.any(KtAnnotationEntry::isDynamicPreview)

private fun KtAnnotationEntry.inheritsDynamicPreview(): Boolean {
    val context = this.analyze(BodyResolveMode.PARTIAL)
    val annotationDescriptor = context.get(BindingContext.ANNOTATION, this)
    val annotationTypeDescriptor = annotationDescriptor?.type?.constructor?.declarationDescriptor
    return (annotationTypeDescriptor as? DeclarationDescriptor)
        ?.let { DescriptorToSourceUtils.descriptorToDeclaration(it) as? KtClass }
        ?.hasDynamicPreview()
        ?: false
}
