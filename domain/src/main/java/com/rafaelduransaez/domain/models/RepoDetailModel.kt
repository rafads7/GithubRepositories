package com.rafaelduransaez.domain.models

data class RepoDetailModel(
    val id: Int,
    val name: String = "",
    val description: String = "",
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    val language: String = "",
    val url: String,
    val favourite: Boolean = false,
    val owner: UserDetailModel
)