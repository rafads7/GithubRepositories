package com.rafaelduransaez.githubrepositories.framework.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = true) val repoId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)