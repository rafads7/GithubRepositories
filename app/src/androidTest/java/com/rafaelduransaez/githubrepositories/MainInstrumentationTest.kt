package com.rafaelduransaez.githubrepositories

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.getIdlingResources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import com.rafaelduransaez.githubrepositories.ui.classical.MainActivity
import com.rafaelduransaez.githubrepositories.utils.MockWebServerRule
import com.rafaelduransaez.githubrepositories.utils.OkHttp3IdlingResource
import com.rafaelduransaez.githubrepositories.utils.fromJson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class MainInstrumentationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(MockResponse().fromJson("repos.json"))
        hiltRule.inject()

/*       activityRule.scenario.onActivity {
            val mIdlingResource = IdlingRegistry.getInstance().resources.asIterable().first()
            IdlingRegistry.getInstance().register(mIdlingResource)
        }*/

        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun click_on_a_repository_navigates_to_repository_detail() = runTest {

        advanceUntilIdle()

        onView(withId(R.id.list)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.action_button)).check(matches(withText(R.string.str_go_to_github)))
    }

}