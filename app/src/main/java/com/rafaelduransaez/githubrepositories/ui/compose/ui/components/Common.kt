package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconComponent(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String = "Content description"
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier,
    )
}