package com.rafaelduransaez.domain.models

data class RepoModel(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    val language: String = "",
    var favourite: Boolean = false
)