package com.rafaelduransaez.githubrepositories.framework.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourite_repo")
data class FavouriteRepoEntity(
    @PrimaryKey @ColumnInfo(defaultValue = "0") val githubId: Int = 0,
    @ColumnInfo(defaultValue = "") val name: String,
    @ColumnInfo(defaultValue = "") val description: String,
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    @ColumnInfo(defaultValue = "") val language: String,
    @ColumnInfo(defaultValue = "") val url: String,
    val ownerId: Int
)