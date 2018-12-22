package com.thuillier.guillaume.moodtracker.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import static android.content.Context.MODE_PRIVATE;

/**
 * This class handles every action with SharedPreferences : write and read.
 */

class HistorySharedPreferences {

    private SharedPreferences mPreferences;
    private static final String KEY_FILE_PREFERENCES = "History.History";
    private static final String KEY_MOOD = "mood : ";
    private static final String KEY_COMMENT = "comment : ";
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");

    HistorySharedPreferences( @NonNull Context context){
        mPreferences = context.getSharedPreferences(KEY_FILE_PREFERENCES, MODE_PRIVATE);
    }

    void saveActualMood(int location, @Nullable String comment){

        mPreferences.edit().putInt(KEY_MOOD + getStringDate().format(formatter), location).apply();
        mPreferences.edit().putString(KEY_COMMENT + getStringDate().format(formatter), comment).apply();
    }

    private LocalDate getStringDate(){

        return LocalDate.now();
    }

    int todayMood(){

        return mPreferences.getInt(KEY_MOOD + getStringDate().format(formatter), 3);
    }

    @Nullable String todayComment(){

        return mPreferences.getString(KEY_COMMENT + getStringDate().format(formatter), null);
    }

    int getMoodXDaysAgo(int daysAgo){

        return mPreferences.getInt(KEY_MOOD + getStringDate().minusDays(daysAgo).format(formatter), 3);
    }

    String getCommentXDaysAgo(int daysAgo){

        return mPreferences.getString(KEY_COMMENT + getStringDate().minusDays(daysAgo).format(formatter), null);
    }
}
