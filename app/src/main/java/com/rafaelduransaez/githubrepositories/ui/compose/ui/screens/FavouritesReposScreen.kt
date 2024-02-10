package com.rafaelduransaez.githubrepositories.ui.compose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.toAnnotatedString
import com.rafaelduransaez.githubrepositories.ui.screen.list.RepositoriesViewModel

@Composable
fun FavouritesReposScreen(
    viewModel: RepositoriesViewModel = hiltViewModel(),
    onRepoClicked: (Int) -> Unit
) {
    val repos by viewModel.favRepos.collectAsState(initial = emptyList())

    LazyColumn {
        items(repos) {
            FavRepoItem(it) { repoId ->
                onRepoClicked(repoId)
            }
        }
    }
}

@Composable
fun FavRepoItem(
    repo: RepositoryDetail,
    modifier: Modifier = Modifier,
    onRepoClicked: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onRepoClicked(repo.id) }
            .clip(RoundedCornerShape(25.dp))
    ) {
        Column {
            AsyncImage(
                model = repo.owner.avatarUrl, contentDescription = "Owner avatar",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
            )
            with(repo) {
                Text(text = toAnnotatedString(), Modifier.padding(8.dp))
            }
        }
    }
}