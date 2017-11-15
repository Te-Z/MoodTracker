package com.zafindratafa.terence.moodtracker.Model;

import android.provider.Settings;

import com.zafindratafa.terence.moodtracker.Model.Mood;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.zafindratafa.terence.moodtracker.Model.Mood.moodDayComparator;
import static org.junit.Assert.*;

/**
 * Created by maverick on 15/11/17.
 */
public class MoodTest {
    private Mood m1, m2, m3, m4, m5, m6;
    private List<Mood> mMoodList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        m1 = new Mood(2, 1, null);
        m2 = new Mood(2, 7, "Coucou");
        m3 = new Mood(2, 5, " comment ");
        m4 = new Mood(2, 3, "123");
        m5 = new Mood(2, 2, "  ");
        m6 = new Mood(2, 22, null);

        mMoodList.add(m1);
        mMoodList.add(m2);
        mMoodList.add(m3);
        mMoodList.add(m4);
        mMoodList.add(m5);
        mMoodList.add(m6);
    }

    @Test
    public void getDate() throws Exception {
        int t1 = m1.getDate();
        assertTrue("m1.getDate = 1", t1 == 1);
    }

    @Test
    public void setDate() throws Exception {
        m1.setDate(0);
        assertTrue("m1.setDate(0)", m1.getDate() == 0);
    }

    @Test
    public void getNote() throws Exception {
        assertTrue("mood's note: null", m1.getNote() == null);
        assertTrue("mood's note: Coucou", m2.getNote() == "Coucou");
    }

    @Test
    public void getMood() throws Exception {
        assertTrue("mood selected: 2", m1.getMood() == 2);
    }

    @Test
    public void sortTest() throws Exception {
        Collections.sort(mMoodList, moodDayComparator);
        int[] dayArray = new int[6];
        int count = 0;
        for (Mood md: mMoodList){
            dayArray[count] = md.getDate();
            count += 1;
        }

        int[] testArray = {1, 2, 3, 5, 7, 22};

        assertArrayEquals(testArray, dayArray);
    }

}