package com.rafaelduransaez.data.mockEntities

import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import org.mockito.Mockito

fun buildMockRepoDetail() = RepositoryDetail(
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

fun buildMockUserDetail() = UserDetail("Mock username", "Mock avatarUrl")

fun buildMockRepo(
    localDS: GithubReposLocalDataSource = Mockito.mock(),
    remoteMediatorDS: GithubReposMediatorDataSource = Mockito.mock()
) = GithubRepository(localDS, remoteMediatorDS)