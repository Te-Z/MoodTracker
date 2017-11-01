package com.zafindratafa.terence.moodtracker;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager mViewPager;
    private CustomSwipeAdapter mAdapter;
    private int mCurrentItem = 2;

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
    }
}
