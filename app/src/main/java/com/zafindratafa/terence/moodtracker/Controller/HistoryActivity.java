package com.zafindratafa.terence.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zafindratafa.terence.moodtracker.Model.Mood;
import com.zafindratafa.terence.moodtracker.Model.Serialize;
import com.zafindratafa.terence.moodtracker.R;
import com.zafindratafa.terence.moodtracker.View.CustomAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private File mFolder, moodFile;
    private FileInputStream fis;
    private FileOutputStream fos;
    private List<Mood> moodLog;
    private ObjectInputStream mInputStream;
    private ObjectOutputStream mOutputStream;
    private ListAdapter mAdapter;
    private ListView mListView;
    private Serialize ser;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Deserialize moodLog
        mFolder = new File(getFilesDir() + "/mood");
        if (!mFolder.exists()){
            mFolder.mkdir();
        }
        moodFile = new File(mFolder.getAbsolutePath() + "/moodLog1.dat");
        ser = new Serialize();
        moodLog = ser.deserialize(moodFile);

        // check what's is moodLog
        if(!moodLog.isEmpty()){
            mAdapter = new CustomAdapter(this, moodLog);
            mListView = (ListView)findViewById(R.id.history_listview);
            mListView.setAdapter(mAdapter);
        } else {
            // if moodLog is empty, display a message to the user
            mTextView = (TextView)findViewById(R.id.empty_list);
            mTextView.setVisibility(View.VISIBLE);
        }
    }
}