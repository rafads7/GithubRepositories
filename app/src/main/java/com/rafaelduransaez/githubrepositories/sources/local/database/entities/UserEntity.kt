package com.rafaelduransaez.githubrepositories.sources.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("users")
data class UserEntity(
    @PrimaryKey val id: Int,
    @SerializedName("avatar_user") val avatarUrl: String,
    @SerializedName("login") val userName: String,
    @SerializedName("repos_url") val reposUrl: String,
    val type: String,
    val url: String
)