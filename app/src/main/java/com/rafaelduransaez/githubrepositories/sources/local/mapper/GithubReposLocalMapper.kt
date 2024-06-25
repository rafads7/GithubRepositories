package com.rafaelduransaez.githubrepositories.sources.local.mapper

import com.rafaelduransaez.githubrepositories.sources.local.database.entities.KeyEntity
import com.rafaelduransaez.githubrepositories.sources.local.database.entities.RepoEntity

fun List<RepoEntity>.toKeyEntityList(prevKey: Int?, nextKey: Int?) = map {
    it.toKeyEntity(prevKey, nextKey)
}

fun RepoEntity.toKeyEntity(prevKey: Int?, nextKey: Int?) =
    KeyEntity(repoId = id, prevKey = prevKey, nextKey = nextKey)
