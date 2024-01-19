package com.rafaelduransaez.domain

data class RepositoryDetail(
    val name: String = "",
    val description: String = "",
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    val language: String = "",
    val url: String,
    val owner: UserDetail
)