package com.zafindratafa.terence.moodtracker.Controller;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomSwipeAdapter;
import com.zafindratafa.terence.moodtracker.View.VerticalViewPager;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager mViewPager;
    private CustomSwipeAdapter mAdapter;
    private int mCurrentItem = 2;
    private ImageButton mAddNote, mHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (VerticalViewPager)findViewById(R.id.view_pager);
        mAdapter = new CustomSwipeAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentItem);
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
}
