package com.rafaelduransaez.data.mockEntities

import com.rafaelduransaez.data.repositories.GithubReposRepositoryImpl
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.models.RepoDetailModel
import com.rafaelduransaez.domain.models.UserDetailModel
import org.mockito.Mockito

fun buildMockRepoDetail() = RepoDetailModel(
    id = 1,
    name = "Mock name",
    description = "Mock description",
    starsCount = 0,
    forksCount = 0,
    language = "Mock language",
    url = "Mock url",
    favourite = false,
    owner = buildMockUserDetail()
)

fun buildMockUserDetail() = UserDetailModel("Mock username", "Mock avatarUrl")

fun buildMockRepo(
    localDS: GithubReposLocalDataSource = Mockito.mock(),
    remoteMediatorDS: GithubReposMediatorDataSource = Mockito.mock()
) = GithubReposRepositoryImpl(localDS, remoteMediatorDS)