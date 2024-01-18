package com.rafaelduransaez.githubrepositories.utils

import com.rafaelduransaez.domain.Error
import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.domain.RepositoryDetail
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.remote.RemoteRepo
import retrofit2.HttpException
import java.io.IOException

fun RemoteRepo.toRepository() =
    Repository(id, name, description ?: "", stargazers_count, forks_count, language ?: "")

fun RemoteRepo.toRepoEntity() =
    RepoEntity(name = name,
        description = description ?: "",
        starsCount = stargazers_count,
        forksCount = forks_count,
        language = language ?: "",
        url = url
    )

fun RepoEntity.toRepository() =
    Repository(id, name, description ?: "", starsCount, forksCount, language ?: "")

fun RepoEntity.toRepoDetail() =
    RepositoryDetail(name, description ?: "", starsCount, forksCount, language ?: "", url ?: "")

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