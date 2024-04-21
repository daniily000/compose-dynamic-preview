package com.daniily.preview.sample.preview

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.daniily.preview.DynamicPreview
import com.daniily.preview.sample.theme.SampleTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, backgroundColor = 0xFFFFFFFF)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF1A1A1A)
annotation class SamplePreview

@DynamicPreview
@DynamicPreview.PreviewClass(SamplePreview::class)
@DynamicPreview.Wrapper(SampleDynamicPreview.Companion::class)
annotation class SampleDynamicPreview {

    companion object {

        @Composable
        fun Wrapper(content: @Composable () -> Unit) {
            SampleTheme {
                Column {
                    content()
                }
            }
        }
    }
}
