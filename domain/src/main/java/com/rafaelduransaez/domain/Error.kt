package com.rafaelduransaez.domain

sealed interface Error {
    data class Server(val code: Int) : Error
    data object Connection : Error
    data object Database: Error
    data object Activity: Error
    data class Unknown(val message: String) : Error
}
