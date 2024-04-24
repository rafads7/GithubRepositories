package com.rafaelduransaez.githubrepositories.framework.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class KeyEntity(
    @PrimaryKey(autoGenerate = true) val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)