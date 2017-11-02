package com.zafindratafa.terence.moodtracker.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomSwipeAdapter;
import com.zafindratafa.terence.moodtracker.View.VerticalViewPager;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager mViewPager;
    private CustomSwipeAdapter mAdapter;
    private int mCurrentMood;
    private ImageButton mAddNote, mHistory;
    private TextView mTextView;

    public static final String MOOD_OF_THE_DAY = "mood_of_the_day";
    public static final String BUNDLE_STATE_MOOD = "usersMood";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (VerticalViewPager)findViewById(R.id.view_pager);
        mAdapter = new CustomSwipeAdapter(this);
        mViewPager.setAdapter(mAdapter);
        // load the last position of the day
        if(savedInstanceState != null) {
            mCurrentMood = savedInstanceState.getInt(BUNDLE_STATE_MOOD);
        } else {
            mCurrentMood = getPreferences(MODE_PRIVATE).getInt(MOOD_OF_THE_DAY, 2);
            //TODO: mCurrentMoodDay = getPreferences(MODE_PRIVATE).getInt(DAY_OF_THE_MOOD, ???);
            //TODO: mCurrentMoodNote = getPreferences(MODE_PRIVATE).getString(MOOD_NOTE, null);

            //TODO: if mCurrentMoodDay != CurrentDay
            //TODO: create a new mood object with mCurrentMood, mCurrentMoodNote and mCurrentMoodDay as arguments
            //TODO: save it in a List[] moodLog
            //TODO: serialize moodlog (moodlog.size() < 8)
            //TODO: set mCurrentMood = 2;
            //TODO: set mCurrentMoodNote = null;
        }

        mTextView = (TextView)findViewById(R.id.textview_test);
        mTextView.setText("Last selected view: "+mCurrentMood);

        mViewPager.setCurrentItem(mCurrentMood);
        //TODO: play a sound each time the view change
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println(mViewPager.getCurrentItem());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAddNote = (ImageButton)findViewById(R.id.add_note);
        mAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show a popup where the user can write a comment
            }
        });

        mHistory = (ImageButton)findViewById(R.id.history_display);
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(BUNDLE_STATE_MOOD, mCurrentMood);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        // Save currentMood when exiting the app
        preferences.edit().putInt(MOOD_OF_THE_DAY, mViewPager.getCurrentItem()).apply();
        //TODO: Save currentMood's date
        //TODO: Save currentMood's note
        super.onStop();
    }
}
