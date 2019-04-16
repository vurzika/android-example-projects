package com.viktorija.intents;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    // Test whether the SecondActivity View elements appear when clicking the Button
    @Test
    public void activityLaunch() {
        // Adding a combined ViewMatcher and ViewAction expression to the activityLaunch() method
        // to locate the View containing the button_main Button, and include a ViewAction expression to perform a click
        onView(withId(R.id.button_main)).perform(click());

        // ViewMatcher expression to find the text_header View (which is in SecondActivity),
        // and a ViewAction expression to perform a check to see if the View is displayed:
        onView(withId(R.id.text_header)).check(matches(isDisplayed()));

        // testing whether clicking the button_second Button in SecondActivity switches to MainActivity:
        onView(withId(R.id.button_second)).perform(click());
        onView(withId(R.id.text_header_reply)).check(matches(isDisplayed()));
    }


    // Test text input and output
    @Test
    public void textInputOutput() {
        onView(withId(R.id.editText_main)).perform(typeText("This is a test."));
        onView(withId(R.id.button_main)).perform(click());
        onView(withId(R.id.text_message)).check(matches(withText("This is a test.")));
    }
}
