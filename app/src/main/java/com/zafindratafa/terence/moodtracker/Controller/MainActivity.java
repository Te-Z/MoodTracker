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

import com.zafindratafa.terence.moodtracker.Model.Mood;
import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomSwipeAdapter;
import com.zafindratafa.terence.moodtracker.View.VerticalViewPager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CustomSwipeAdapter mAdapter;
    private EditText mEditText;
    private File mFolder, moodFile;
    private ImageButton mAddNote, mHistory;
    private int mCurrentMood, mCurrentMoodDay, mCurrentDay;
    private List<Mood> moodLog = new ArrayList<>();
    private Mood newMood;
    private ObjectInputStream mInputStream;
    private ObjectOutputStream mOutputStream;
    private String mCurrentMoodNote, mNote;
    private TextView mMood_test, mNote_test, mDate_test;
    private VerticalViewPager mViewPager;

    public static final String MOOD_OF_THE_DAY = "mood_of_the_day";
    public static final String MOOD_NOTE = "mood_note";
    public static final String MOOD_DAY = "mood_day";

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
        // load the last position of the day
        if(savedInstanceState != null) {
            // prevents the rotation
            mCurrentMood = savedInstanceState.getInt(BUNDLE_STATE_MOOD);
            mCurrentMoodNote = savedInstanceState.getString(BUNDLE_STATE_NOTE);
            mCurrentMoodDay = savedInstanceState.getInt(BUNDLE_STATE_DAY);
        } else {
            mCurrentMood = getPreferences(MODE_PRIVATE).getInt(MOOD_OF_THE_DAY, 2);
            mCurrentMoodNote = getPreferences(MODE_PRIVATE).getString(MOOD_NOTE, null);
            mCurrentMoodDay = getPreferences(MODE_PRIVATE).getInt(MOOD_DAY, 1);
        }

        // check if it has to save the previous mood
        Calendar calendar = Calendar.getInstance();
        mCurrentDay = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println("CurrentMoodDay: "+mCurrentMoodDay +" CurrentDay: "+mCurrentDay);

        if (mCurrentMoodDay != mCurrentDay ){
            save(mCurrentMood, mCurrentMoodDay, mCurrentMoodNote);
            mCurrentMood = 2;
            mCurrentMoodNote = null;
        }

        // Test TextViews
        mMood_test = (TextView)findViewById(R.id.textview_test);
        mMood_test.setText("Last selected view: "+mCurrentMood);

        mNote_test = (TextView)findViewById(R.id.textview_testNote);
        mNote_test.setText("Last note: "+mCurrentMoodNote);

        mDate_test = (TextView)findViewById(R.id.textview_testDate);
        mDate_test.setText("Day n°"+mCurrentMoodDay);

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
        outState.putInt(BUNDLE_STATE_DAY, mCurrentMoodDay);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        // Save currentMood when exiting the app
        preferences.edit().putInt(MOOD_OF_THE_DAY, mViewPager.getCurrentItem()).apply();
        // Save currentMood's date
        Calendar cal = Calendar.getInstance();
        preferences.edit().putInt(MOOD_DAY, cal.get(Calendar.DAY_OF_YEAR)).apply();
        super.onStop();
    }

    private void save(int mood, int date, String note){
        // create a newMood object with mCurrentMood, mCurrentMoodNote and mCurrentMoodDay as arguments
        newMood = new Mood(mood, date, note);
        mFolder = new File(getFilesDir() + "/mood");
        if (!mFolder.exists()){
            mFolder.mkdir();
        }
        moodFile = new File(mFolder.getAbsolutePath() + "/moodLog.dat");

        if(moodFile.exists()){
            try{
                // deserialize moodLog
                System.out.println("moodLog.dat exists");

                FileInputStream fis = new FileInputStream(moodFile);
                mInputStream = new ObjectInputStream(fis);

                moodLog = (ArrayList<Mood>) mInputStream.readObject();
                System.out.println("moodLog before serialization: "+moodLog.toString());

                // if there's an other mood saved today delete it
                for (Iterator<Mood> it = moodLog.iterator(); it.hasNext(); ){
                    Mood md = it.next();
                    if (md.getDate() == newMood.getDate()){
                        it.remove();
                    }
                }

                // moodLog's size must be lower than 7 to add a new mood in the log
                while (moodLog.size() > 6){
                    moodLog.remove(0);
                }

                serializeMood(moodLog, newMood);
            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        } else {
            try{
                System.out.println("moodLog.dat has to be created");
                moodFile.createNewFile();
                serializeMood(moodLog, newMood);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void serializeMood(List<Mood> moodLog, Mood md){
        // add newMood in moodLog then serialize moodLog
        try{
            FileOutputStream fos = new FileOutputStream(moodFile);
            mOutputStream = new ObjectOutputStream(fos);

            moodLog.add(md);
            System.out.println("Moodlog after serialization: "+moodLog.toString());

            mOutputStream.writeObject(moodLog);
            mOutputStream.flush();
            mOutputStream.close();
            System.out.println("Serialization: OK");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
