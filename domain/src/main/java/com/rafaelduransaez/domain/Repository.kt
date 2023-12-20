package com.rafaelduransaez.domain

data class Repository(
    val id: Int,
    val name: String,
    val description: String,
    val starsCount: Int,
    val forksCount: Int,
    val language: String
)