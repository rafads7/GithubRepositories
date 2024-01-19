package com.rafaelduransaez.githubrepositories.framework.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("repositories")
data class RepoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(defaultValue = "") val name: String,
    @ColumnInfo(defaultValue = "") val description: String,
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    @ColumnInfo(defaultValue = "") val language: String,
    @ColumnInfo(defaultValue = "") val url: String,
    val ownerId: Int
)