package com.rafaelduransaez.githubrepositories.sources.local.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RepoUserEntity(
    @Embedded val repo: RepoEntity,
    @Relation(
        parentColumn = "ownerId", //RepoEntity_ownerId
        entityColumn = "id" //UserEntity_id
    )
    val user: UserEntity
    )