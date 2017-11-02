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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (VerticalViewPager)findViewById(R.id.view_pager);
        mAdapter = new CustomSwipeAdapter(this);
        mViewPager.setAdapter(mAdapter);
        // load the last position of the day
        //TODO: check if the currentPosition was setted up today
        //TODO: if the currentPosition is yesterday's, save it in the log file with his comment and the date
        mCurrentMood = getPreferences(MODE_PRIVATE).getInt(MOOD_OF_THE_DAY, 2);

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
    protected void onStop() {
        // Save currentMood when exiting the app
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        preferences.edit().putInt(MOOD_OF_THE_DAY, mViewPager.getCurrentItem()).apply();
        super.onStop();
    }
}
