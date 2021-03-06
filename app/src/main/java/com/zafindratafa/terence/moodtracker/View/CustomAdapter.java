package com.zafindratafa.terence.moodtracker.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zafindratafa.terence.moodtracker.Model.Mood;
import com.zafindratafa.terence.moodtracker.R;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.List;

/**
 * Created by maverick on 08/11/17.
 *
 * This class set the content of the HistoryActivity based on
 * elements stored on each Mood objects set inside history_custom_row
 */

public class CustomAdapter extends ArrayAdapter<Mood>{

    private int rowWidth, width, deviceWidth, height, deviceHeight, moodPos, mDaysPassed, color;
    private final Display mDisplay;
    private LayoutInflater moodInflater;
    private ImageButton mImageButton;
    private Mood singleMood;
    private Point deviceDisplay;
    private RelativeLayout rowLayout;
    private String moodDate;
    private TextView mTextView;
    private View customView;

    public CustomAdapter(Context context, List<Mood> moodLog){
        super(context, R.layout.history_custom_row, moodLog);
        // Compute device's width and height
        mDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        deviceDisplay = new Point();
        mDisplay.getSize(deviceDisplay);
        deviceWidth = deviceDisplay.x;
        deviceHeight = deviceDisplay.y;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        moodInflater = LayoutInflater.from(getContext());
        customView = moodInflater.inflate(R.layout.history_custom_row, parent, false);
        singleMood = getItem(position);

        // set the date
        mTextView = (TextView)customView.findViewById(R.id.history_row_textview);
        mTextView.setText(getMoodDate(singleMood));

        // display the comment button if there's one
        mImageButton = (ImageButton)customView.findViewById(R.id.note_button);
        final String note = singleMood.getNote();
        if (note != null){
            mImageButton.setVisibility(View.VISIBLE);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), note, Toast.LENGTH_LONG).show();
                }
            });
        }

        // set row's color, width and height
        rowLayout = (RelativeLayout)customView.findViewById(R.id.Row_layout);
        moodPos = singleMood.getMood();
        height = deviceHeight/7;
        width = deviceWidth/5;
        switch (moodPos){
            case 0: color = Color.parseColor("#ffde3c50");
                    rowWidth = width * 1;
                    break;
            case 1: color = Color.parseColor("#ff9b9b9b");
                    rowWidth = width * 2;
                    break;
            case 2: color = Color.parseColor("#a5468ad9");
                    rowWidth = width * 3;
                    break;
            case 3: color = Color.parseColor("#ffb8e986");
                    rowWidth = width * 4;
                    break;
            case 4: color = Color.parseColor("#fff9ec4f");
                    rowWidth = width * 5;
                    break;
            default: color = Color.BLACK;
                    rowWidth = 0;
                    break;
        }
        rowLayout.setBackgroundColor(color);
        resizeView(customView, rowWidth, height-10);

        return customView;
    }

    // allow getView to access the dates
    private String getMoodDate(Mood mood){
        mDaysPassed = mood.getDate();

        if (mDaysPassed == 1) {
            moodDate = "Hier";
        } else if (mDaysPassed == 2) {
            moodDate = "Avant-hier";
        } else if (mDaysPassed >2 && mDaysPassed <7){
            moodDate = "Il y a "+mDaysPassed+" jours";
        } else if (mDaysPassed == 7){
            moodDate = "Il y a une semaine";
        } else {
            moodDate = "Il y a plus d'une semaine";
        }

        return moodDate;
    }

    // Resize each row
    private void resizeView(View view, int newWidth, int newHeight){
        try{
            Constructor<? extends ViewGroup.LayoutParams> ctor = view.getLayoutParams().getClass().getDeclaredConstructor(int.class, int.class);
            view.setLayoutParams(ctor.newInstance(newWidth, newHeight));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
