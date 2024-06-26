package com.rafaelduransaez.githubrepositories.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.rafaelduransaez.githubrepositories.GithubRepositoriesApp
import com.rafaelduransaez.githubrepositories.R
import com.rafaelduransaez.githubrepositories.framework.local.database.GithubReposDatabase
import com.rafaelduransaez.githubrepositories.framework.local.database.entities.RepoEntity
import com.rafaelduransaez.githubrepositories.framework.mediator.GithubReposRemoteMediator
import com.rafaelduransaez.githubrepositories.framework.remote.GithubReposService
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
    fun provideService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): GithubReposService {

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideReposPager(database: GithubReposDatabase, apiService: GithubReposService): Pager<Int, RepoEntity> =
        Pager(
            config = PagingConfig(GithubReposService.per_page.toInt()),
            remoteMediator = GithubReposRemoteMediator(database, apiService),
            pagingSourceFactory = { database.reposDao().pagingRepos() }
        )


    @Provides
    @Singleton
    fun provideDatabase(app: Application): GithubReposDatabase = Room.databaseBuilder(
        app,
        GithubReposDatabase::class.java,
        "github_repos_database"
    ).build()

    @Provides
    @Singleton
    fun provideGithubReposDao(db: GithubReposDatabase) = db.reposDao()

    @Provides
    @Singleton
    fun provideFavReposDao(db: GithubReposDatabase) = db.favouriteRepoDao()


    @ColorArray
    @Provides
    fun provideColorsArray(): IntArray {
        return intArrayOf(R.array.random_colors)
    }


}