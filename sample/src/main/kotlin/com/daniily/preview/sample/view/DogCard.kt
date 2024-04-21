package com.daniily.preview.sample.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.daniily.preview.DynamicPreviewParameter
import com.daniily.preview.sample.R
import com.daniily.preview.sample.preview.SampleDynamicPreview

@OptIn(ExperimentalMaterialApi::class)
@Composable
@SampleDynamicPreview
fun DogCard(
    @DynamicPreviewParameter
    name: String,
    @DynamicPreviewParameter
    description: String,
    @DynamicPreviewParameter
    starred: Boolean,
    @DynamicPreviewParameter
    highlighted: Boolean,
    modifier: Modifier = Modifier,
    image: Painter = painterResource(id = R.drawable.dog1),
    onClick: () -> Unit = {},
    onStarClick: () -> Unit = {},
) {
    val targetBackgroundColor = if (highlighted) {
        MaterialTheme.colors.primaryVariant.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colors.surface
    }
    val backgroundColor by animateColorAsState(targetValue = targetBackgroundColor)
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        backgroundColor = backgroundColor,
        modifier = modifier
    ) {
        Column {
            Box(
                modifier = Modifier.drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, backgroundColor),
                                startY = size.height * 0.6f,
                                endY = size.height
                            )
                        )
                    }
                }
            ) {
                Image(
                    painter = image,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null,
                    modifier = Modifier.heightIn(max = 160.dp)
                )
                IconButton(onClick = onStarClick) {
                    Crossfade(targetState = starred) {
                        val icon = if (it) Icons.Filled.Star else Icons.Outlined.Add
                        val tint = if (it) {
                            MaterialTheme.colors.secondary
                        } else {
                            MaterialTheme.colors.secondaryVariant
                        }
                        Icon(
                            imageVector = icon,
                            tint = tint,
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
