package com.zafindratafa.terence.moodtracker.Model;

import java.io.Serializable;

/**
 * Created by maverick on 02/11/17.
 */

public class Mood implements Serializable{
    private String note;
    private int date, mood;

    public Mood(int mood, int date){
        this.mood = mood;
        this.date = date;
    }

    public Mood(int mood, int date, String note){
        this.mood = mood;
        this.date = date;
        this.note = note;
    }

    public int getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Mood position: "+mood+", day of the year: "+date+" note: "+note;
    }
}
