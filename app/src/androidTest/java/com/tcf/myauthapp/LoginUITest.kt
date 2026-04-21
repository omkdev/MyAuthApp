package com.tcf.myauthapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUITest {

    // This tells the test to launch LoginActivity before starting
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun loginScreen_isDisplayed() {
        // Check if the App Name is visible
        onView(withText("MyAuthApp")).check(matches(isDisplayed()))

        // Check if the login button is visible and has the right text
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
        onView(withId(R.id.btnLogin)).check(matches(withText("Sign in with Keycloak")))
    }
}
