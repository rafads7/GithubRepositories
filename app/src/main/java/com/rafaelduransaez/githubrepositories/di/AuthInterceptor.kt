package com.rafaelduransaez.githubrepositories.di

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "token $authToken")
            .build()
        return chain.proceed(request)
    }
}
