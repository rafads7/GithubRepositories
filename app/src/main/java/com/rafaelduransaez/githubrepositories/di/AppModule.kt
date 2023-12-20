package com.rafaelduransaez.githubrepositories.di

import android.app.Application
import com.rafaelduransaez.githubrepositories.GithubRepositoriesApp
import com.rafaelduransaez.githubrepositories.framework.api.RepositoriesService
import com.rafaelduransaez.githubrepositories.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiAccessToken
    fun provideApiAccessTokenKey(): String = Constants.api_access_token + Constants.api_access_token_ending

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = Constants.api_url

    @Provides
    @Singleton
    fun provideApplication(app: Application) = app as GithubRepositoriesApp

    @Provides
    fun provideAuthInterceptor(@ApiAccessToken accessToken: String): AuthInterceptor {
        return AuthInterceptor(accessToken)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient =
        HttpLoggingInterceptor().run {
            level = HttpLoggingInterceptor.Level.BODY
            (OkHttpClient.Builder()
                .addInterceptor(this)
                .addInterceptor(authInterceptor)
                .build())
        }

    @Provides
    @Singleton
    fun provideService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): RepositoriesService {

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

}