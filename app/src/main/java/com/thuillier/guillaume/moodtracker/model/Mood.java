package com.thuillier.guillaume.moodtracker.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import com.thuillier.guillaume.moodtracker.R;

public enum Mood {
    VERY_SAD (R.color.faded_red, R.mipmap.smiley_sad),
    SAD (R.color.warm_grey, R.mipmap.smiley_disappointed),
    NORMAL (R.color.cornflower_blue_65, R.mipmap.smiley_normal),
    HAPPY (R.color.light_sage, R.mipmap.smiley_happy),
    VERY_HAPPY (R.color.banana_yellow, R.mipmap.smiley_super_happy);

    @ColorRes
    int mNumberColor;
    @DrawableRes
    int mSmileyImage;

    Mood(@ColorRes int numberColor, @DrawableRes int mSmileyImage) {
        mNumberColor = numberColor;
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
}
