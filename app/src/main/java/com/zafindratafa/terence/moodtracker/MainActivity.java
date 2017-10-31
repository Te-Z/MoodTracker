package com.zafindratafa.terence.moodtracker;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    CustomSwipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mAdapter = new CustomSwipeAdapter(this);
        mViewPager.setAdapter(mAdapter);
    }
}
