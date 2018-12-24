package com.thuillier.guillaume.moodtracker.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.thuillier.guillaume.moodtracker.R;
import com.thuillier.guillaume.moodtracker.model.*;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_MOOD = Mood.values().length;
    private static final String TYPE_INTENT_SHARE = "text/plain";

    private ImageView mSmileyImage;
    private RelativeLayout mMainBackground;
    private GestureDetector mGestureListener;
    private OnFlingListener mOnFlingListener;
    private int mLocationInt = (MAX_MOOD+1) /2;
    private HistorySharedPreferences mHistory;
    private String mCommentMood = null;
    private Mood mActualMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        ImageButton noteAddButton = findViewById(R.id.activity_main_note_add_button);
        ImageButton historyButton = findViewById(R.id.activity_main_history_button);
        ImageButton shareTv = findViewById(R.id.activity_main_share_button);

        mSmileyImage = findViewById(R.id.activity_main_image_smiley);
        mMainBackground = findViewById(R.id.activity_main_background);

        mOnFlingListener = new OnFlingListener();
        mGestureListener = new GestureDetector(this, mOnFlingListener);
        mHistory = new HistorySharedPreferences(this);

        mLocationInt = mHistory.todayMood();
        mCommentMood = mHistory.todayComment();
        changeMoodVisual(mLocationInt);

        noteAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogComment();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent (MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });

        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMood();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event){

        boolean consumedEvent = mGestureListener.onTouchEvent(event);
        float deltaY;

        if (consumedEvent){
            deltaY = mOnFlingListener.getDeltaY();

            if(deltaY > 0){

                swipeUp();
            }

            else{

                swipeDown();
            }

            return true;
        }
        else return false;
    }

    public void onStop(){

        saveActualMood();
        super.onStop();
    }

    private void swipeUp(){

        if (mLocationInt + 1 < MAX_MOOD) {
            mLocationInt++;
            changeMoodVisual(mLocationInt);
        }
    }

    private void swipeDown(){

        if(mLocationInt - 1  >= 0) {
            mLocationInt--;
            changeMoodVisual(mLocationInt);
        }
    }

    /**
     * This method change everything that depends on the active mood : smiley, background color, background color of buttons.
     * @param index = mLocationInt, that allow program to know which mood to display.
     */
    private void changeMoodVisual(int index){

        mActualMood = Mood.values()[index];

        mSmileyImage.setImageResource(mActualMood.getSmileyImage());
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, mActualMood.getNumberColor()));
        saveActualMood();
    }

    /**
     * Method that create and display a dialog box. The user can write a comment, it will be saved and display when wanted.
     */
    private void alertDialogComment(){

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);

        String todayComment = mHistory.todayComment();

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        adBuilder.setView(input);
        adBuilder.setTitle(R.string.comment);

        if (todayComment != null){
            input.setText(todayComment);
        }

        adBuilder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCommentMood = input.getText().toString();
                saveActualMood();
            }
        });

        adBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        adBuilder.show();
    }


    /**
     * Method that allow to share the description of the mood with the comment (if it exists).
     */
    private void shareMood(){

        String descriptionMood = getString(mActualMood.getDescription());
        String stringToShare = R.string.share_hello_first_part + descriptionMood + R.string.share_hello_second_part;

        if (mCommentMood != null){
            stringToShare += R.string.here_is_why + mCommentMood;
        }

        Intent shareMood = new Intent(Intent.ACTION_SEND);
        shareMood.setType(TYPE_INTENT_SHARE);
        shareMood.putExtra(Intent.EXTRA_TEXT, stringToShare);
        startActivity(shareMood);
    }

    private void saveActualMood(){
        mHistory.saveActualMood(mLocationInt, mCommentMood);
    }
}
