package com.rafaelduransaez.domain

sealed interface Error {
    class Server(val code: Int) : Error
    data object Connection : Error
    data object Database: Error
    class Unknown(val message: String) : Error
}