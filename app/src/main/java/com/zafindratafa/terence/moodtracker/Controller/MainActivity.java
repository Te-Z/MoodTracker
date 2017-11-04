package com.zafindratafa.terence.moodtracker.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomSwipeAdapter;
import com.zafindratafa.terence.moodtracker.View.VerticalViewPager;

public class MainActivity extends AppCompatActivity {
    private VerticalViewPager mViewPager;
    private CustomSwipeAdapter mAdapter;
    private int mCurrentMood;
    private String mCurrentMoodNote, mNote;
    private ImageButton mAddNote, mHistory;
    private TextView mMood_test, mNote_test, mDate_test;
    private EditText mEditText;

    public static final String MOOD_OF_THE_DAY = "mood_of_the_day";
    public static final String MOOD_NOTE = "mood_note";
    public static final String BUNDLE_STATE_MOOD = "usersMood";
    public static final String BUNDLE_STATE_NOTE = "usersNote";

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
            mCurrentMoodNote = savedInstanceState.getString(BUNDLE_STATE_NOTE);
        } else {
            mCurrentMood = getPreferences(MODE_PRIVATE).getInt(MOOD_OF_THE_DAY, 2);
            mCurrentMoodNote = getPreferences(MODE_PRIVATE).getString(MOOD_NOTE, null);
            //TODO: mCurrentMoodDay = getPreferences(MODE_PRIVATE).getInt(DAY_OF_THE_MOOD, ???);

            //TODO: if mCurrentMoodDay != CurrentDay
            //TODO: create a new mood object with mCurrentMood, mCurrentMoodNote and mCurrentMoodDay as arguments
            //TODO: save it in a List[] moodLog
            //TODO: serialize moodlog (moodlog.size() < 8)
            //TODO: set mCurrentMood = 2;
            //TODO: set mCurrentMoodNote = null;
        }

        // Test TextViews
        mMood_test = (TextView)findViewById(R.id.textview_test);
        mMood_test.setText("Last selected view: "+mCurrentMood);

        mNote_test = (TextView)findViewById(R.id.textview_testNote);
        mNote_test.setText("Last note: "+mCurrentMoodNote);

        mDate_test = (TextView)findViewById(R.id.textview_testDate);
        mDate_test.setText("Date");

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
                // show a popup where the user can write a comment
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.note_layout, null);

                mEditText = (EditText) mView.findViewById(R.id.note_text);

                builder.setView(mView);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Save the comment if it exists
                        mNote = mEditText.getText().toString();
                        if(!mNote.isEmpty()){
                            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                            preferences.edit().putString(MOOD_NOTE, mNote).apply();
                            Toast.makeText(MainActivity.this,
                                    "Note enregistrée !",
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
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
        outState.putString(BUNDLE_STATE_NOTE, mCurrentMoodNote);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        // Save currentMood when exiting the app
        preferences.edit().putInt(MOOD_OF_THE_DAY, mViewPager.getCurrentItem()).apply();
        //TODO: Save currentMood's date
        super.onStop();
    }
}
