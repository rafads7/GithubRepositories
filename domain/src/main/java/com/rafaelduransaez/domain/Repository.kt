package com.rafaelduransaez.domain

data class Repository(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val starsCount: Int = 0,
    val forksCount: Int = 0,
    val language: String = ""
)