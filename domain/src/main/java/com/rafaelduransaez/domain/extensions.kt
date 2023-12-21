package com.rafaelduransaez.domain

fun RemoteRepo.toRepository() = Repository(id, name, description, stargazers_count, forks_count, language)