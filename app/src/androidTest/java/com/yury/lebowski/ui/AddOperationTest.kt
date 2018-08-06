package com.yury.lebowski.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.yury.lebowski.MainActivity
import com.yury.lebowski.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddOperationTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun openAddFragmentAndEmptyClickAndToast() {
        val activity = activityTestRule.activity
        onView(allOf(withId(R.id.speedDial), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.add_income), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.add_button), isDisplayed())).perform(click())
        onView(withText(R.string.incorrect_data)).inRoot(withDecorView(not((activity.getWindow().getDecorView())))).check(matches(isDisplayed()))
    }

    @Test
    fun openAddFragmentAndOperationAndToast() {
        val activity = activityTestRule.activity
        onView(allOf(withId(R.id.speedDial), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.add_income), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.moneyEditText), isDisplayed())).perform(typeText("100.0"), closeSoftKeyboard())
        onView(allOf(withId(R.id.add_button), isDisplayed())).perform(click())
        onView(withText(R.string.successfully_added)).inRoot(withDecorView(not((activity.getWindow().getDecorView())))).check(matches(isDisplayed()))
    }

}