package com.thuillier.guillaume.moodtracker.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.threeten.bp.LocalDate;

import static android.content.Context.MODE_PRIVATE;
import static com.thuillier.guillaume.moodtracker.utils.Tools.*;

/**
 * This class handles every action with SharedPreferences : write and read.
 */

class HistorySharedPreferences {

    private final static String KEY_FILE_PREFERENCES = "History.History";
    private final static String KEY_MOOD = "mood : ";
    private final static String KEY_COMMENT = "comment : ";

    private SharedPreferences mPreferences;

    HistorySharedPreferences( @NonNull Context context){
        mPreferences = context.getSharedPreferences(KEY_FILE_PREFERENCES, MODE_PRIVATE);
    }

    void saveActualMood(int location, @Nullable String comment){

        mPreferences.edit().putInt(KEY_MOOD + getDate().format(formatter), location).apply();
        mPreferences.edit().putString(KEY_COMMENT + getDate().format(formatter), comment).apply();
    }

    private LocalDate getDate(){

        return LocalDate.now();
    }

    int todayMood(){

        return mPreferences.getInt(KEY_MOOD + getDate().format(formatter), 3);
    }

    @Nullable String todayComment(){

        return mPreferences.getString(KEY_COMMENT + getDate().format(formatter), null);
    }

    int getMoodXDaysAgo(int daysAgo){

        return mPreferences.getInt(KEY_MOOD + getDate().minusDays(daysAgo).format(formatter), 3);
    }

    String getCommentXDaysAgo(int daysAgo){

        return mPreferences.getString(KEY_COMMENT + getDate().minusDays(daysAgo).format(formatter), null);
    }
}
