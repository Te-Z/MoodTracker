package com.zafindratafa.terence.moodtracker.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zafindratafa.terence.moodtracker.Model.Mood;
import com.zafindratafa.terence.moodtracker.Model.Preferences;
import com.zafindratafa.terence.moodtracker.Model.Serialize;
import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomSwipeAdapter;
import com.zafindratafa.terence.moodtracker.View.VerticalViewPager;

import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CustomSwipeAdapter mAdapter;
    private EditText mEditText;
    private File mFolder, moodFile;
    private ImageButton mAddNote, mHistory;
    private int mCurrentMood;
    private List<Mood> moodLog = new ArrayList<>();
    private Mood newMood;
    private ObjectInputStream mInputStream;
    private ObjectOutputStream mOutputStream;
    private Preferences userPref;
    private String mCurrentMoodNote, mNote, mCurrentDay, mCurrentMoodDay;
    private Serialize serial;
    private VerticalViewPager mViewPager;

    public static final String BUNDLE_STATE_MOOD = "usersMood";
    public static final String BUNDLE_STATE_NOTE = "usersNote";
    public static final String BUNDLE_STATE_DAY = "MoodsDay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (VerticalViewPager)findViewById(R.id.view_pager);
        mAdapter = new CustomSwipeAdapter(this);
        mViewPager.setAdapter(mAdapter);

        // check if it has to save the previous mood
        mCurrentDay = new LocalDate().toString();

        // load the last position of the day

        userPref = new Preferences(this);
        if(savedInstanceState != null) {
            // prevents the rotation
            mCurrentMood = savedInstanceState.getInt(BUNDLE_STATE_MOOD);
            mCurrentMoodNote = savedInstanceState.getString(BUNDLE_STATE_NOTE);
            mCurrentMoodDay = savedInstanceState.getString(BUNDLE_STATE_DAY);
        } else {
            mCurrentMood = userPref.getMoodPref();
            mCurrentMoodNote = userPref.getNotePref();
            mCurrentMoodDay = userPref.getDayPref();
        }

        System.out.println("CurrentMoodDay: "+mCurrentMoodDay +" CurrentDay: "+mCurrentDay);

        // if last saved day is different than current's, save previous Mood object
        LocalDate sDate = new LocalDate(LocalDate.parse(mCurrentMoodDay));
        LocalDate cDate = new LocalDate(LocalDate.parse(mCurrentDay));
        Period period = new Period(sDate, cDate);
        int deltaDays = period.toStandardDays().getDays();
        if (deltaDays != 0){
            mFolder = new File(getFilesDir() + "/mood");
            if (!mFolder.exists()){
                mFolder.mkdir();
            }
            moodFile = new File(mFolder.getAbsolutePath() + "/moodLog1.dat");
            serial = new Serialize();
            moodLog = serial.deserialize(moodFile);

            newMood = new Mood(mCurrentMood, deltaDays, mCurrentMoodNote);
            serial.serialize(moodLog, newMood, moodFile);

            mCurrentMood = 2;
            mCurrentMoodNote = null;
            mCurrentMoodDay = mCurrentDay;
        }

        mViewPager.setCurrentItem(mCurrentMood);
        // play a sound each time the view change
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println(position);
                int ind = mViewPager.getCurrentItem();
                MediaPlayer mp;
                switch (ind){
                    case 0: mp = MediaPlayer.create(MainActivity.this, R.raw.sound0);
                            break;
                    case 1: mp = MediaPlayer.create(MainActivity.this, R.raw.sound1);
                            break;
                    case 2: mp = MediaPlayer.create(MainActivity.this, R.raw.sound2);
                            break;
                    case 3: mp = MediaPlayer.create(MainActivity.this, R.raw.sound3);
                            break;
                    case 4: mp = MediaPlayer.create(MainActivity.this, R.raw.sound4);
                            break;
                    default: mp = MediaPlayer.create(MainActivity.this, R.raw.sound2);
                             break;
                }
                mp.start();
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
                        // set mNote value and confirm to user
                        mNote = mEditText.getText().toString();
                        if(!mNote.isEmpty()){
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
        // go to historyActivity
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }
        });
    }

    // save current data if the activity has to create again on a screen rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(BUNDLE_STATE_MOOD, mCurrentMood);
        outState.putString(BUNDLE_STATE_NOTE, mCurrentMoodNote);
        outState.putString(BUNDLE_STATE_DAY, mCurrentMoodDay);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        // When the user turn off the app...
        // ...save current mood,...
        int moodItem = mViewPager.getCurrentItem();
        userPref.setMoodPref(moodItem);
        // ...save currentMood's date...
        String date = new LocalDate().toString();
        userPref.setDayPref(date);
        // ...and save currentMood's comment.
        userPref.setNotePref(mNote);
        super.onStop();
    }
}
