package com.zafindratafa.terence.moodtracker.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.zafindratafa.terence.moodtracker.Model.Mood.moodDayComparator;
import static org.junit.Assert.*;

/**
 * Created by maverick on 15/11/17.
 */
public class SerializeTest {
    private File moodFile;
    private List<Mood> mMoodList, mMoodListTest;
    private Mood m1, m2, m3, m4, m5, m6, m7, m8;
    private ObjectOutputStream mOutputStream;
    private Serialize ser;

    @Before
    public void setUp() throws Exception {
        moodFile = new File("testLog.dat");
        mMoodList = new ArrayList<>();
        mMoodListTest = new ArrayList<>();
        ser = new Serialize();

        m1 = new Mood(2, 1, null);
        m2 = new Mood(2, 7, "Coucou");
        m3 = new Mood(2, 5, " comment ");
        m4 = new Mood(2, 3, "123");
        m5 = new Mood(2, 2, null);
        m6 = new Mood(2, 22, null);
        m7 = new Mood(2, 23, null);
        m8 = new Mood(2, 25, null);
    }

    @After
    public void tearDown() throws Exception {
        mMoodList.clear();
        try{
            FileOutputStream fos = new FileOutputStream(moodFile);
            mOutputStream = new ObjectOutputStream(fos);

            mOutputStream.writeObject(mMoodList);
        } catch (IOException e){
            e.printStackTrace();
        }
        mMoodListTest.clear();
    }

    @Test
    public void serializeTest1() throws Exception {
        mMoodList.add(m1);
        mMoodList.add(m2);
        mMoodList.add(m3);
        mMoodList.add(m4);
        mMoodList.add(m5);
        mMoodList.add(m6);

        ser.serialize(mMoodList, m7, moodFile);
        mMoodList= ser.deserialize(moodFile);

        mMoodListTest.add(m1);
        mMoodListTest.add(m2);
        mMoodListTest.add(m3);
        mMoodListTest.add(m4);
        mMoodListTest.add(m5);
        mMoodListTest.add(m6);
        mMoodListTest.add(m7);
        Collections.sort(mMoodListTest, moodDayComparator);
        System.out.println(mMoodListTest.toString());

        for(int i = 0; i < mMoodListTest.size(); i++){
            assertTrue("Date object n°"+i, mMoodListTest.get(i).getDate() == mMoodList.get(i).getDate());
            assertTrue("Mood object n°"+i, mMoodListTest.get(i).getMood() == mMoodList.get(i).getMood());
        }
    }

    @Test
    public void serializeTest2() throws Exception {
        mMoodList.add(m1);
        mMoodList.add(m2);
        mMoodList.add(m3);
        mMoodList.add(m4);
        mMoodList.add(m5);
        mMoodList.add(m6);
        mMoodList.add(m7);

        ser.serialize(mMoodList, m8, moodFile);
        mMoodList= ser.deserialize(moodFile);

        mMoodListTest.add(m2);
        mMoodListTest.add(m3);
        mMoodListTest.add(m4);
        mMoodListTest.add(m5);
        mMoodListTest.add(m6);
        mMoodListTest.add(m7);
        mMoodListTest.add(m8);
        Collections.sort(mMoodListTest, moodDayComparator);
        System.out.println(mMoodListTest.toString());

        for(int i = 0; i < mMoodListTest.size(); i++){
            assertTrue("Date object n°"+i, mMoodListTest.get(i).getDate() == mMoodList.get(i).getDate());
            assertTrue("Mood object n°"+i, mMoodListTest.get(i).getMood() == mMoodList.get(i).getMood());
        }
    }

    @Test
    public void serializeTest3() throws Exception {
        mMoodList.add(m1);
        mMoodList.add(m2);
        mMoodList.add(m3);
        mMoodList.add(m4);
        mMoodList.add(m5);
        mMoodList.add(m6);
        mMoodList.add(m7);

        ser.serialize(mMoodList, m8, moodFile);
        mMoodList= ser.deserialize(moodFile);

        int[] dayArray = new int[7];
        int count = 0;
        for (Mood md: mMoodList){
            dayArray[count] = md.getDate();
            count += 1;
        }

        int[] testArray = {25, 27, 28, 30, 32, 47, 48};

        System.out.println(dayArray);
        System.out.println(testArray);

        assertArrayEquals(testArray, dayArray);
    }

    @Test
    public void deserializeTest() throws Exception {
        File serTest = new File("test.dat");
        ser.deserialize(serTest);
    }

}