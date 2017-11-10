package com.zafindratafa.terence.moodtracker.Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by maverick on 02/11/17.
 */

// this is the Mood object's skull
public class Mood implements Serializable{
    private String note;
    private int date, mood;

    private static final long serialVersionUID = 201711051500L;

    public Mood(){
    }

    public Mood(int mood, int date, String note){
        this.mood = mood;
        this.date = date;
        this.note = note;
    }

    public int getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public int getMood() {
        return mood;
    }

    @Override
    public String toString() {
        return "Mood position: "+mood+", day of the year: "+date+" note: "+note;
    }

    // Compare multiple dates
    public static Comparator<Mood> moodDayComparator = new Comparator<Mood>() {
        @Override
        public int compare(Mood o1, Mood o2) {
            Calendar cal = Calendar.getInstance();
            int minDaysOfYear = cal.getActualMinimum(Calendar.DAY_OF_YEAR);
            int maxDaysOfYear = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
            int moodDay1 = o1.getDate();
            int moodDay2 = o2.getDate();

            // prevent the end and the beginning of a year
            if((moodDay1 >= minDaysOfYear && moodDay1 < minDaysOfYear +6)
                    && (moodDay2 <= maxDaysOfYear && moodDay2 > maxDaysOfYear - 6)){
                moodDay1 += maxDaysOfYear;
            }

            return moodDay2 - moodDay1;
        }
    };
}
