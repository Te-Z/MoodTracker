package com.zafindratafa.terence.moodtracker.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.zafindratafa.terence.moodtracker.R;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.espresso.Espresso.onView;

import org.hamcrest.core.AllOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by maverick on 09/11/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void clickAddNoteButton_opensAddNoteUi() throws Exception {
        onView(withId(R.id.add_note))
                .perform(click());
        onView(withId(R.id.note_text))
                .check(matches(isDisplayed()));
    }

    @Test
    public void defaultMoodTest() throws Exception{
        onView(withText("#a5468ad9")).check(matches(isDisplayed()));
    }

    @Test
    public void swipeUpTest() throws Exception{
        onView(withId(R.id.activity_main_layout)).perform(swipeUp());
        Thread.sleep(1000);
        onView(withText("#ffb8e986")).check(matches(isDisplayed()));
    }

    @Test
    public void swipeDownTest() throws Exception{
        onView(withId(R.id.activity_main_layout)).perform(swipeDown());
        Thread.sleep(1000);
        onView(withText("#ff9b9b9b")).check(matches(isDisplayed()));
    }

    @Test
    public void clicOpenHistoryActivity() throws Exception{
        onView(withId(R.id.history_display)).perform(click());
        onView(withId(R.id.history_listview)).check(matches(isDisplayed()));
    }
}