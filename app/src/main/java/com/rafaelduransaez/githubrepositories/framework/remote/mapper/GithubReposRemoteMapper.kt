package com.rafaelduransaez.githubrepositories.framework.remote.mapper

import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.remote.entities.GithubRepoRemoteEntity
import com.rafaelduransaez.githubrepositories.framework.remote.entities.GithubReposRemoteEntityResponse
import com.rafaelduransaez.githubrepositories.utils.toUserEntity

fun GithubReposRemoteEntityResponse.toRepoEntityList() =
    repos.map { it.toRepoEntity() }

fun GithubRepoRemoteEntity.toRepoEntity() =
    RepoEntity(
        name = name,
        githubId = id,
        description = description.orEmpty(),
        starsCount = stargazers_count,
        forksCount = forks_count,
        language = language.orEmpty(),
        url = html_url,
        ownerId = owner.id
    )

fun GithubReposRemoteEntityResponse.toUserEntityList() =
    repos.distinctBy { it.owner.id }.map { it.owner.toUserEntity() }

