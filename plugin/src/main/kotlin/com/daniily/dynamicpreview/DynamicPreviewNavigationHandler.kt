package com.daniily.dynamicpreview

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.codeInsight.daemon.impl.PsiElementListNavigator
//import com.intellij.grazie.utils.dropPostfix
import com.intellij.ide.util.DefaultPsiElementCellRenderer
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.idea.core.util.toPsiFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import java.awt.event.MouseEvent

internal class DynamicPreviewNavigationHandler : GutterIconNavigationHandler<KtNamedFunction> {

    private fun getPreviewFunction(of: KtNamedFunction): KtNamedFunction? {
//        val baseName = of.containingKtFile.name.dropPostfix(".kt")

        val ktFileName = of.containingKtFile.name
        val baseName = if (ktFileName.endsWith(".kt")) ktFileName.dropLast(3) else ktFileName

        val previewFunctionName = "${baseName}_DynamicPreview"
        val previewFilename = "$previewFunctionName.kt"
//        return FilenameIndex.getVirtualFilesByName(previewFilename, GlobalSearchScope.allScope(of.project))
        return FilenameIndex.getVirtualFilesByName(of.project, previewFilename, GlobalSearchScope.allScope(of.project))
            .firstOrNull()
            ?.toPsiFile(of.project)
            ?.children
            ?.filterIsInstance<KtNamedFunction>()
            ?.find { it.name == previewFunctionName }
    }

    fun isPreviewFunctionAvailable(of: KtNamedFunction): Boolean = getPreviewFunction(of) != null

    override fun navigate(event: MouseEvent, element: KtNamedFunction) {
        val previewFunction: KtNamedFunction = getPreviewFunction(element) ?: return
        PsiElementListNavigator.openTargets<NavigatablePsiElement>(
            /* e = */ event,
            /* targets = */ arrayOf(previewFunction),
            /* title = */ "Navigate to Preview",
            /* findUsagesTitle = */ null,
            /* listRenderer = */ DefaultPsiElementCellRenderer()
        )
    }

}