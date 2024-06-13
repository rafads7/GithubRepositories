package com.rafaelduransaez.githubrepositories.ui.compose.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.githubrepositories.ui.compose.ui.components.toAnnotatedString
import com.rafaelduransaez.githubrepositories.ui.classical.list.RepositoriesViewModel

@Composable
fun FavouritesReposScreen(
    minimizedGrid: Boolean = false,
    viewModel: RepositoriesViewModel = hiltViewModel(),
    onRepoClicked: (Int) -> Unit
) {
    val repos by viewModel.favRepos.collectAsState(initial = emptyList())

    AnimatedVisibility(visible = true,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(
                if (minimizedGrid) 2 else 1
            )
        ) {
            items(repos) {
                FavRepoItem(it) { repoId ->
                    onRepoClicked(repoId)
                }
            }
        }
    }


}

@Composable
fun FavRepoItem(
    repo: RepoDetailModel,
    modifier: Modifier = Modifier,
    onRepoClicked: (Int) -> Unit
) {
    ElevatedCard(
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
