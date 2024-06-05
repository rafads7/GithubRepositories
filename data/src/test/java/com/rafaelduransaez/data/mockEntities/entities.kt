package com.rafaelduransaez.data.mockEntities

import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.PagerDataSource
import com.rafaelduransaez.domain.RepoDetailModel
import com.rafaelduransaez.domain.UserDetailModel
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
    remoteMediatorDS: PagerDataSource = Mockito.mock()
) = GithubRepository(localDS, remoteMediatorDS)