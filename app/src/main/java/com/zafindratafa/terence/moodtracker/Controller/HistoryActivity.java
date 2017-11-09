package com.zafindratafa.terence.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zafindratafa.terence.moodtracker.Model.Mood;
import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private File mFolder, moodFile;
    private FileInputStream fis;
    private FileOutputStream fos;
    private List<Mood> moodLog;
    private Mood mMood;
    private ObjectInputStream mInputStream;
    private ObjectOutputStream mOutputStream;
    private ListAdapter mAdapter;
    private ListView mListView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Deserialize moodLog
        mMood = new Mood();
        deserialize();
        Collections.sort(moodLog, mMood.moodDayComparator);
        System.out.println("MoodLog sorted: "+moodLog);

        if(!moodLog.isEmpty()){
            mAdapter = new CustomAdapter(this, moodLog);
            mListView = (ListView)findViewById(R.id.history_listview);
            mListView.setAdapter(mAdapter);
        } else {
            mTextView = (TextView)findViewById(R.id.empty_list);
            mTextView.setVisibility(View.VISIBLE);
        }
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
                System.out.println("moodLog1.dat has to be created");
                moodFile.createNewFile();

                fos = new FileOutputStream(moodFile);
                mOutputStream = new ObjectOutputStream(fos);

                moodLog = new ArrayList<Mood>();

                mOutputStream.writeObject(moodLog);
                mOutputStream.flush();
                mOutputStream.close();
                System.out.println("moodLog created and serialized");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}