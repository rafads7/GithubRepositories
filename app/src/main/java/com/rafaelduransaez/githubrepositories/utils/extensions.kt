package com.rafaelduransaez.githubrepositories.utils

import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.domain.UserDetail
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoUser
import com.rafaelduransaez.githubrepositories.framework.database.entities.UserEntity
import com.rafaelduransaez.githubrepositories.framework.remote.entities.RemoteOwner
import com.rafaelduransaez.githubrepositories.framework.remote.entities.RemoteRepo
import retrofit2.HttpException
import java.io.IOException

fun RemoteRepo.toRepoEntity() =
    RepoEntity(name = name,
        description = description ?: "",
        starsCount = stargazers_count,
        forksCount = forks_count,
        language = language ?: "",
        url = html_url,
        ownerId = owner.id
    )

fun RemoteOwner.toUserEntity() = UserEntity(id, avatarUrl, login, repos_url, type, url)

fun RepoEntity.toRepository() =
    Repository(id, name, description ?: "", starsCount, forksCount, language ?: "")

fun RepoUser.toRepositoryDetail() = RepositoryDetail(
    repo.name, repo.description, repo.starsCount, repo.forksCount, repo.language, repo.url,
    UserDetail(user.userName, user.avatarUrl)
)


fun String.truncate(limit: Int): String {
    return if (length > limit) {
        substring(0, limit - 1) + "..."
    } else {
        this
    }
}

fun Throwable.toError(): Error = when (this) {
    is IOException -> Error.Connection
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}