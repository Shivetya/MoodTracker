package com.thuillier.guillaume.moodtracker.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.thuillier.guillaume.moodtracker.R;

/**
 * This enum allow us to keep every information of each mood in one place.
 * We have here each smiley image, colors, percent for bars in HistoryActivity and description used for the sharing.
 * mNumberColor must be ColorRes, mSmileyImage must be DrawableRes.
 */

public enum Mood {
    VERY_SAD (R.color.faded_red, R.drawable.smiley_sad, 0.2f, R.string.very_bad),
    SAD (R.color.warm_grey, R.drawable.smiley_disappointed, 0.4f, R.string.bad),
    NORMAL (R.color.cornflower_blue_65, R.drawable.smiley_normal, 0.6f, R.string.normal),
    HAPPY (R.color.light_sage, R.drawable.smiley_happy,0.8f,R.string.happy),
    VERY_HAPPY (R.color.banana_yellow, R.drawable.smiley_super_happy,1f, R.string.very_happy);

        @ColorRes
        int mNumberColor;

        @DrawableRes
        int mSmileyImage;

        float mPercent;

        @StringRes
        int mDescription;

        Mood(@ColorRes int numberColor, @DrawableRes int smileyImage, float percent, @StringRes int description) {
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

    public float getPercent() {
        return mPercent;
    }

    @StringRes
    public int getDescription() {
        return mDescription;
    }
}
