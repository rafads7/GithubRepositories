package com.rafaelduransaez.githubrepositories.entities

import com.rafaelduransaez.data.repositories.GithubRepository
import com.rafaelduransaez.data.datasources.GithubReposLocalDataSource
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.domain.RepoDetailModel
import com.rafaelduransaez.domain.UserDetailModel
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoUserEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.UserEntity
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

fun buildMockRepoUser(repo: RepoEntity = mockRepoEntity, user: UserEntity = mockUserEntity) =
    RepoUserEntity(repo, user)

val mockRepoEntity: RepoEntity = RepoEntity(
    1, 1, "name", "desc", 1, 1,
    "language", "url", 1, false
)
val mockUserEntity: UserEntity = UserEntity(1, "", "", "", "", "")
fun buildMockRepo(
    localDS: GithubReposLocalDataSource = Mockito.mock(),
    remoteMediatorDS: GithubReposMediatorDataSource = Mockito.mock()
) = GithubRepository(localDS, remoteMediatorDS)