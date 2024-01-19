package com.rafaelduransaez.githubrepositories.framework.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RepoUser(
    @Embedded val repo: RepoEntity,
    @Relation(
        parentColumn = "ownerId", //RepoEntity_ownerId
        entityColumn = "id" //UserEntity_id
    )
    val user: UserEntity,
    )