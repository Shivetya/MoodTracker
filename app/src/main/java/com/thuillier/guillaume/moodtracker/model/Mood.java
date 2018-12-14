package com.thuillier.guillaume.moodtracker.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import com.thuillier.guillaume.moodtracker.R;

public enum Mood {
    VERY_SAD (R.color.faded_red, R.mipmap.smiley_sad, 0.2f, "très mauvaise"),
    SAD (R.color.warm_grey, R.mipmap.smiley_disappointed, 0.4f, "mauvaise"),
    NORMAL (R.color.cornflower_blue_65, R.mipmap.smiley_normal, 0.6f, "normale"),
    HAPPY (R.color.light_sage, R.mipmap.smiley_happy,0.8f,"heureuse"),
    VERY_HAPPY (R.color.banana_yellow, R.mipmap.smiley_super_happy,1f, "très heureuse");

        @ColorRes
        int mNumberColor;

        @DrawableRes
        int mSmileyImage;

        float mPercent;
        String mDescription;

        Mood(@ColorRes int numberColor, @DrawableRes int smileyImage, float percent, String description) {
            mNumberColor = numberColor;
            mSmileyImage = smileyImage;
            mPercent = percent;
            mDescription = description;
        }

    @ColorRes
    public int getNumberColor() {
        return mNumberColor;
    }

    @DrawableRes
    public int getSmileyImage() {
        return mSmileyImage;
    }

    public static Mood fromValues(Integer number) {
        return Mood.values()[number];
    }

    public float getPercent() {
        return mPercent;
    }

    public String getDescription() {
        return mDescription;
    }
}
