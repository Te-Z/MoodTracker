package com.zafindratafa.terence.moodtracker.Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by maverick on 15/11/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PreferencesTest {

    private Preferences preferences;

    @Before
    public void setUp() throws Exception {
        preferences = new Preferences(RuntimeEnvironment.application);
    }

    @Test
    public void getMoodPref() throws Exception {
        preferences.setMoodPref(2);
        assertTrue("getMoodPref", preferences.getMoodPref() == 2);
    }

    @Test
    public void getNotePref() throws Exception {
        preferences.setNotePref("Coucou");
        assertTrue("getNotePref", preferences.getNotePref() == "Coucou");
    }

    @Test
    public void getDayPref() throws Exception {
        preferences.setDayPref("2017-11-15");
        assertTrue("getDayPref", preferences.getDayPref() == "2017-11-15");
    }

}