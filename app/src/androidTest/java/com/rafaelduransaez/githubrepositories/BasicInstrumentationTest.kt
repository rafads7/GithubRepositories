package com.rafaelduransaez.githubrepositories

import androidx.paging.testing.asSnapshot
import com.rafaelduransaez.apptestshared.mockRepoEntity
import com.rafaelduransaez.apptestshared.mockRepoEntityFav
import com.rafaelduransaez.data.datasources.GithubReposMediatorDataSource
import com.rafaelduransaez.githubrepositories.framework.local.database.dao.ReposDao
import com.rafaelduransaez.githubrepositories.utils.MockWebServerRule
import com.rafaelduransaez.githubrepositories.utils.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class BasicInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @Inject
    lateinit var reposDao: ReposDao

    @Inject
    lateinit var remoteDataSource: GithubReposMediatorDataSource

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(MockResponse().fromJson("repos.json"))
        hiltRule.inject()
    }

    @Test
    fun test_server_works() = runTest {
        // Do not know why this test is not emitting any values, so it is failing...
        // Same error than in Integration test: kotlinx.coroutines.test.UncompletedCoroutinesError:
        // After waiting for 10s, the test coroutine is not completing, there were active child jobs:
        // [ScopeCoroutine{Active}@360356a]

        val data = remoteDataSource.reposPager().asSnapshot()
        assertEquals(10, data.size)

/*        viewModel.bestRatedRepos.test {
            val data = viewModel.bestRatedRepos.asSnapshot()
            assertEquals(mockRepoList, data)
            cancelAndIgnoreRemainingEvents()
        }*/
    }

    @Test
    fun test_insert_db() = runTest {
        reposDao.upsert(listOf(mockRepoEntity, mockRepoEntityFav))
        assertEquals(2, reposDao.count())
    }

    @Test
    fun test_insert_db_do_not_accumulate_for_testing_purposes() = runTest {
        reposDao.upsert(listOf(mockRepoEntity, mockRepoEntityFav, mockRepoEntity.copy(id = 3)))
        assertEquals(3, reposDao.count())
    }

}