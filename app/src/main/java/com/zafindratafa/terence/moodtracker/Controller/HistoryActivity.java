package com.zafindratafa.terence.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zafindratafa.terence.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //TODO: Deserialize moodLog
        //TODO: for each date (starting with the closest), display a horizontal bar
        //TODO: if there's a comment with the mood, display an icon on the bar
        //TODO: when the user click on the comment's icon, the comment appear in the bottom via a Toast
    }
}
