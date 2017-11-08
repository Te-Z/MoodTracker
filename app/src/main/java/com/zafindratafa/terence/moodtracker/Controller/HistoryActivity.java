package com.zafindratafa.terence.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.zafindratafa.terence.moodtracker.Model.Mood;
import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private File mFolder, moodFile;
    private FileInputStream fis;
    private List<Mood> moodLog;
    private Mood mMood;
    private ObjectInputStream mInputStream;
    private ListAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Deserialize moodLog
        moodLog = new ArrayList<Mood>();
        mMood = new Mood();
        deserialize();
        Collections.sort(moodLog, mMood.moodDayComparator);
        System.out.println("MoodLog sorted: "+moodLog);

        mAdapter = new CustomAdapter(this, moodLog);
        mListView = (ListView)findViewById(R.id.history_listview);
        mListView.setAdapter(mAdapter);
        //TODO: for each date (starting with the closest), display a horizontal bar
        //TODO: if there's a comment with the mood, display an icon on the bar
        //TODO: when the user click on the comment's icon, the comment appear in the bottom via a Toast
    }

    private void deserialize(){
        mFolder = new File(getFilesDir() + "/mood");
        if(!mFolder.exists()){
            mFolder.mkdir();
        }
        moodFile = new File(mFolder.getAbsolutePath() + "/moodLog1.dat");
        if(moodFile.exists()){
            try{
                System.out.println("HistoryActivity: moodLog1.dat exists");

                fis = new FileInputStream(moodFile);
                mInputStream = new ObjectInputStream(fis);

                moodLog = (ArrayList<Mood>) mInputStream.readObject();
                System.out.println("moodLog's content: "+moodLog.toString());

            } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        } else {
            try{
                moodFile.createNewFile();
                moodLog = new ArrayList<Mood>();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}