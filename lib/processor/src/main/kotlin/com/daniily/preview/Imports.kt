package com.daniily.preview

import com.squareup.kotlinpoet.ClassName

internal object Imports {
    const val composeRuntime = "androidx.compose.runtime"
    const val composeMaterial = "androidx.compose.material"
    const val foundationLayout = "androidx.compose.foundation.layout"
    const val composeUi = "androidx.compose.ui"
    const val devexpertsPreview = "com.devexperts.aurora.mobile.android.preview"
    const val devexpertsTheme = "com.devexperts.aurora.mobile.android.presentation.theme"

    private const val composeToolingPreview = "androidx.compose.ui.tooling.preview"

    val Composable = ClassName(composeRuntime, "Composable")
    val Preview = ClassName(composeToolingPreview, "Preview")
    val Surface = ClassName(composeMaterial, "Surface")
    val Column = ClassName(foundationLayout, "Column")
    val Modifier = ClassName(composeUi, "Modifier")
}
