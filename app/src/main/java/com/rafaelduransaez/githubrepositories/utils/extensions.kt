package com.rafaelduransaez.githubrepositories.utils

import com.rafaelduransaez.domain.Repository
import com.rafaelduransaez.githubrepositories.framework.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.remote.RemoteRepo

fun RemoteRepo.toRepository() =
    Repository(id, name, description ?: "", stargazers_count, forks_count, language ?: "")

fun RemoteRepo.toRepoEntity() =
    RepoEntity(name = name,
        description = description ?: "",
        starsCount = stargazers_count,
        forksCount = forks_count,
        language = language ?: ""
    )

fun RepoEntity.toRepository() =
    Repository(id, name, description ?: "", starsCount, forksCount, language ?: "")