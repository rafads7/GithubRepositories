package com.rafaelduransaez.domain

data class RepositoryDetail(
    val id: Int,
    val name: String = "",
    val description: String = "",
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    val language: String = "",
    val url: String,
    val favourite: Boolean = false,
    val owner: UserDetail
)