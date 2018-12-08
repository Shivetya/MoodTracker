package com.thuillier.guillaume.moodtracker.controller;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.thuillier.guillaume.moodtracker.R;
import com.thuillier.guillaume.moodtracker.model.Mood;
import com.thuillier.guillaume.moodtracker.model.MySimpleGestureListener;

public class MainActivity extends AppCompatActivity {

    private ImageView mSmileyImage;
    private ImageButton mNoteAddButton;
    private ImageButton mHistoryButton;
    private RelativeLayout mMainBackground;
    private GestureDetector mGestureListener;
    private final int MAX_MOOD = 5;
    private int mLocationInt = 3;
    private Mood mActualMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSmileyImage = findViewById(R.id.activity_main_image_smiley);
        mNoteAddButton = findViewById(R.id.activity_main_note_add_button);
        mHistoryButton = findViewById(R.id.activity_main_history_button);
        mMainBackground = findViewById(R.id.activity_main_background);

        mGestureListener = new GestureDetector(this, new MySimpleGestureListener());



    }

    public boolean onTouchEvent(MotionEvent event){

        boolean consumedEvent = mGestureListener.onTouchEvent(event);
        float deltaY;

        if (consumedEvent){
            deltaY = MySimpleGestureListener.sDeltaY;

            if(deltaY > 0) swipeUp();
            else swipeDown();

            return true;
        }
        else return false;
    }


    private void swipeUp(){

        if (mLocationInt < MAX_MOOD - 1) {
            mLocationInt++;
            changeMoodVisual();
        }
    }

    private void swipeDown(){

        if(mLocationInt >= 1) {
            mLocationInt--;
            changeMoodVisual();
        }
    }

    private void changeMoodVisual(){

        mActualMood = Mood.fromValues(mLocationInt);

        switch (mActualMood){

            case VERY_SAD:
                mSmileyImage.setImageResource(R.mipmap.smiley_sad);
                mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.faded_red));
                mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.faded_red));
                mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.faded_red));
                break;

            case SAD:
                mSmileyImage.setImageResource(R.mipmap.smiley_disappointed);
                mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.warm_grey));
                mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.warm_grey));
                mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.warm_grey));
                break;

            case NORMAL:
                mSmileyImage.setImageResource(R.mipmap.smiley_normal);
                mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.cornflower_blue_65));
                mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.cornflower_blue_65));
                mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.cornflower_blue_65));
                break;

            case HAPPY:
                mSmileyImage.setImageResource(R.mipmap.smiley_happy);
                mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sage));
                mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sage));
                mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sage));
                break;

            case VERY_HAPPY:
                mSmileyImage.setImageResource(R.mipmap.smiley_super_happy);
                mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.banana_yellow));
                mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.banana_yellow));
                mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.banana_yellow));
                break;

        }
    }
}
