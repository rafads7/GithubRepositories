package com.rafaelduransaez.githubrepositories.entities

import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.FavouriteRepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoUserEntity
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.UserEntity
import com.rafaelduransaez.githubrepositories.utils.toRepositoryDetail

val mockRepoEntity = RepoEntity(
    1, 1, "name", "desc", 1, 1,
    "language", "url", 1, false
)

val mockRepoEntityFav = mockRepoEntity.copy(id = 2, favourite = true)

val mockUserEntity = UserEntity(1, "", "", "", "", "")

val mockRepoUserEntity = RepoUserEntity(mockRepoEntity, mockUserEntity)

val mockRepoUserEntityFav = RepoUserEntity(mockRepoEntityFav, mockUserEntity)

val mockFavouriteRepoEntity = FavouriteRepoEntity(
    10, "name", "desc", 1, 1, "language", "url", 1
)

val mockRepoDetail = mockRepoUserEntity.toRepositoryDetail()

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

fun buildMockRepository() = Repository(
    id = 0,
    name = "",
    description = "",
    starsCount = 0,
    forksCount = 0,
    language = "",
    favourite = false
)