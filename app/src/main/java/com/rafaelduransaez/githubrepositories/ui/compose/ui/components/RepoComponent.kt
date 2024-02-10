package com.rafaelduransaez.githubrepositories.ui.compose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.ui.toAnnotatedString
import com.rafaelduransaez.githubrepositories.ui.toComposableAnnotatedString

const val MAX_CHAR = 200

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RepoItem(
    repo: Repository = Repository(
        name = "fake name",
        description = "fake desc",
        starsCount = 2,
        forksCount = 4,
        language = "Kotlin"
    ),
    onRepoClicked: () -> Unit = {}
) {

    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.small,
        onClick = { onRepoClicked() }

    ) {
        Row(Modifier.padding(8.dp)) {
            Text(
                text = repo.toComposableAnnotatedString(),
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            if (repo.favourite) {
                IconComponent(imageVector = Icons.Default.Favorite)
            }
        }

    }
}