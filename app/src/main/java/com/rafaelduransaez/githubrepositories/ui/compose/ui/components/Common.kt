package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.rafaelduransaez.domain.models.RepoDetailModel

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

@Composable
fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 16.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (end) append("\n")
    }
}

@Composable
fun RepoDetailModel.toAnnotatedString() = buildAnnotatedString {
    Property(name = "Description", value = description)
    Property(name = "Stars count", value = starsCount.toString())
    Property(name = "Forks count", value = forksCount.toString())
    Property(name = "Language", value = language)
    Property(name = "Owner", value = owner.userName, true)
}